from flask import Flask, request, render_template, send_from_directory, Response
from flask_restful import Api
import pymongo
from bson.objectid import ObjectId
from flask_cors import CORS, cross_origin 
import constants as Const
import os
import glob
import check_value
import json
import datetime
from sendmail import send_mail

app = Flask(__name__)
api = Api(app)
cors = CORS(app)

myclient = pymongo.MongoClient(Const.PATH_MONGO)
mydb = myclient['android']
mycol_user = mydb['user']
mycol_product = mydb['product']
mycol_cart = mydb['cart']
mycol_bill = mydb['bill']
mycol_count = mydb['count']

@app.route('/')
def hello():
    return render_template('index.html')

@app.route('/shop')
def shop():
    return render_template('shop.html')

@app.route('/checkout')
def checkout():
    return render_template('checkout.html')

@app.route('/carts')
def cart():
    _id = request.values.get('id')
    if _id is not None:
        find_cart = mycol_cart.find_one({'_id': _id})
        if find_cart is not None:
            my_cart = find_cart['cart']
            if 'total' in find_cart:
                total = find_cart['total']
                origin = find_cart['origin']
                discount = find_cart['discount']
                return render_template('carts.html', cart=my_cart, origin=origin, discount=discount, total= total)
            return render_template('cart.html', cart = my_cart)
    return render_template('cart.html')

@app.route('/product-details')
def product_details():
    return render_template('product-details.html')

@app.route('/signin')
def signin_account():
    return render_template('signin.html')

@app.route('/signup')
def signup_account():
    return render_template('signup.html')

@app.route('/user')
@cross_origin()
def showdb_user():
    mydoc = mycol_user.find({})
    arr = []
    for x in mydoc:
        x['_id']=str(x['_id'])
        arr.append(x)
    return {
        "status": "success",
        "database": arr
    }

# ==============QUERY-PRODUCT================
@app.route('/product')
@cross_origin()
def showdb_product():
    item = request.values.get('item')
    arr = []
    # -----------------------------------------
    if item == None:
        for x in mycol_product.find({}):
            arr.append(x)

        return {
            "status": "success",
            "data": f"{arr}"
            }
    # -----------------------------------------
    item = item.title()
    list_item = mycol_product.find_one({"type": f'{item}'})

    if list_item == None:
        return {"status": "This product has not been updated yet"}

    list_item = list_item[f'{item}']

    for x in list_item:
        name = x['name']
        price = x['price']
        link = x['link']
        list_ing = {'name': name, 'price': price, 'link': Const.HOST_SERVERS + link}
        arr.append(list_ing)

    return Response(json.dumps(arr),  mimetype='application/json')

# =============QUERY-PRODUCT-VER2=============
@app.route('/products')
@cross_origin()
def showdb_products():
    item = request.values.get('item')
    arr = []
    # -----------------------------------------
    if item == None:
        for x in mycol_product.find({}):
            arr.append(x)

        return {
            "status": "success",
            "data": f"{arr}"
            }
    # -----------------------------------------
    item = item.title()
    list_item = mycol_product.find_one({"type": f'{item}'})

    if list_item == None:
        return {"status": "This product has not been updated yet"}

    list_item = list_item[f'{item}']

    for x in list_item:
        name = x['name']
        price = x['price']
        link = x['link']
        list_ing = {'name': name, 'price': price, 'link': Const.HOST_SERVERS + link}
        arr.append(list_ing)

    return {
        f"{item.lower()}": arr
    }

# ===============UPDATE-PRODUCT================
@app.route('/update_product')
@cross_origin()
def update_product():
    item = request.values.get('item')

    if item == None:
        return {"status": "input query item"}

    item = item.title()
    mycol_product.delete_one({"type": f"{item}"})
    input_directory = Const.PATH_PROJECT + "/" + Const.PATH_PRODUCT + item + "/"

    if not os.path.exists(input_directory):
        return {"status": "This product is not available on the server yet"}

    input_file_extensions = 'JPG'
    files = glob.glob(input_directory+"*."+input_file_extensions)
    arr = []

    for f in files:
        content_item = os.path.basename(f[:-4])
        (name_item, price) = content_item.split("_")
        name_item = name_item.replace("-", " ")
        link = f'/mmt/static/img/{item}/' + os.path.basename(f)
        arr.append({"_id": ObjectId(), "name": name_item, "price": price, "link": link})

    item = {f"{item}": arr, "type": f"{item}"}
    x = mycol_product.insert(item)
    print('Database has been updated!')
    return {    
        "status": "success",
        "database": f'{item}'
    } 

# =======================================

# ===========SIGNIN/SIGNUP-POST===========
@app.route('/signup', methods=['POST'])
@cross_origin()
def save_data_user_post():
    username = request.json['username']
    email = request.json['email']
    password = request.json['password']

    if username == None:
        return {"status": "Please input username!"}
    if email == None:
        return {"status": "Please input email!"}
    if password == None:
        return {"status": "Please input password!"}

    check_username = mycol_user.find_one({"username": str(username)})
    check_email = mycol_user.find_one({"email": str(email)})

    if check_username != None:
        return {
            'status': 'username has been exists'
        }
    if check_email != None:
        return {
            'status': 'email has been exists'
        }

    if username == check_value.check_username(username):
        if email == check_value.check_email(email):
            if password == check_value.check_password(password):
                password = check_value.hash_password(password)
                mycol_user.insert_one({"_id": ObjectId(),"username": str(username), "email": str(email), "password": str(password)})
                return {
                    'status': 'success'
                }
            else:
                msg = check_value.check_password(password)
                return{
                    'status': msg
                }
        else:
            msg = check_value.check_email(email)
            return{
                'status': msg
            }
    else:
        msg = check_value.check_username(username)
        return {
            'status': msg
        }

@app.route('/signin', methods=['POST'])
@cross_origin()
def check_data_user_post():
    username = request.json['username']
    password = request.json['password']
    
    if username == None:
        return {"status": "Please input username!"}
    if password == None:
        return {"status": "Please input password!"}

    x = mycol_user.find_one({"username": username})
    password = check_value.hash_password(password)

    if x != None and x['password'] == password:
        _id = x['_id']
        email = x['email']
        return {
            'status': 'success',
            'id_user': f'{_id}',
            'user_name': f'{username}',
            'email': f'{email}'
        }
    return {
        'status': 'Not found user'
    }

# =================DELETE-USER=================
@app.route('/delete')
@cross_origin()
def delete_user():
    username = request.values.get('username')
    if username != None and mycol_user.find_one({"username": str(username)}):
        mycol_user.delete_one({"username": f'{username}'})
        return{
            "status": f"Delete account successful"
        }

    return {
        "status": "input username to delete account"
    }

# =================UPDATE-USER=================
@app.route('/update', methods=['POST'])
@cross_origin()
def update_user():
    username = request.json['username']
    email = request.json['email']
    password = request.json['password']

    if username == None:
        return {
            "status": "Can not update account user"
        }
        
    else :
        query_username = {"username": f'{username}'}
        if email == None and password == None:
            return {
                "status": "Input email or password to update"
            }

        find_email = mycol_user.find_one({"username": username})

        if email != None and len(email) > 0:
            all_email = mycol_user.find({})
            flag = 0
            for x in all_email:
                if x['email'] == email:
                    flag = 1
            # email != all email
            if flag == 0:
                if password != None and len(password) > 0:
                    if email == check_value.check_email(email):
                        if password == check_value.check_password(password): 
                        # get email & password
                            password = check_value.hash_password(password)
                            set_email_password = {"$set": { "password": f"{password}", "email": f"{email}" }}
                            mycol_user.update_one(query_username, set_email_password )    
                            return {"status": "Account has been updated"}
                        else:
                            msg = check_value.check_password(password)
                            return{
                                'status': msg
                            }
                    else:
                        msg = check_value.check_email(email)
                        return{
                            'status': msg
                        }

                if email == check_value.check_email(email):
                    set_email = {"$set": { "email": f"{email}" }}
                    mycol_user.update_one(query_username, set_email)
                else:
                    msg = check_value.check_email(email)
                    return{
                        'status': msg
                    }

            if email != find_email['email']:
                return {
                    "status": "This email has already exists!"
                }

        if password != None and len(password) > 0:
            if password == check_value.check_password(password): 
                password = check_value.hash_password(password)
                set_password = {"$set": { "password": f"{password}" }}
                mycol_user.update_one(query_username, set_password)   
            else:
                msg = check_value.check_password(password)
                return{
                    'status': msg
                }

        if email == find_email['email'] and password == find_email['password'] or email == find_email['email'] and len(password) == 0 or len(email) == 0 and password == find_email['password'] or len(email) == 0 and len(password) == 0:
            return{
                'status': "No change in account"
            }

        # return chung
        return {
            "status": "Account has been updated"
        }

# =================GET-DATA================== 
@app.route('/mmt/static/img/<folder>/<name>')
@cross_origin()
def show_product(folder, name):
    url = Const.PATH_PROJECT + '/' + Const.PATH_PRODUCT
    if os.path.exists(url):
        return send_from_directory(f'{Const.PATH_PRODUCT}{folder}/', name, mimetype='image/gif')
    return {
        "status": "Not Found"
    }
# ==================================<

# ===============DELETE-ALL================== 
@app.route('/delete/<collections>')
@cross_origin()
def delete_product(collections):
    print(collections, type(collections))
    collections = str(collections)
    if collections.lower() == 'product':
        mycol_product.delete_many({})
        return {
            "status": "success",
            "msg": "Data product deleted successfully"
        }
    if collections.lower() == 'user':
        mycol_user.delete_many({})
        return {
            "status": "success",
            "msg": "Data user deleted successfully"
        }
    if collections.lower() == 'cart':
        mycol_cart.delete_many({})
        return {
            "status": "success",
            "msg": "Data cart deleted successfully"
        }
    if collections.lower() == 'bill':
        mycol_bill.delete_many({})
        return {
            "status": "success",
            "msg": "Data bill deleted successfully"
        }
    if collections.lower() == 'count':
        mycol_count.delete_many({})
        return {
            "status": "success",
            "msg": "Data count deleted successfully"
        }
    return {
        "status": "Not found collections",
    }
        
# ===========================================

# ==================CART===================== 

# - - -  - Watch all products in cart - - - - - 

@app.route('/cart', methods=['GET'])
def show_cart():
    username = request.values.get('username')
    _id = request.values.get('_id')
    arr = []
    if username == None and _id == None:
        for x in mycol_cart.find({}):
            if 'total' in x:
                list_cart = {"_id": x['_id'], "username": x['username'], 'cart': x['cart'], 'total': x['total'], 'origin': x['origin'], 'discount': x['discount']}
            else:
                list_cart = {"_id": x['_id'], "username": x['username'], 'cart': x['cart']}
            arr.append(list_cart)
        return Response(json.dumps(arr),  mimetype='application/json')

    if username is not None:
        if mycol_cart.find_one({'username': username}) == None:
            return {
                    "status": "error",
                    "msg": "Username is not exists"
                }
        data = mycol_cart.find_one({'username': username})
        # list_cart = {"_id": data['_id'], "username": data['username'], 'cart': data['cart']}
        return Response(json.dumps(data['cart']),  mimetype='application/json')
    
    if _id is not None:
        if mycol_cart.find_one({'_id': _id}) == None:
            return {
                    "status": "error",
                    "msg": "Id is not exists"
                }
        data = mycol_cart.find_one({'_id': _id})
        if 'total' in data:
            list_cart = {"_id": data['_id'], "username": data['username'], 'cart': data['cart'], 'total': data['total'], 'origin': x['origin'], 'discount': x['discount']}
        else:
            list_cart = {"_id": data['_id'], "username": data['username'], 'cart': data['cart']}
        return list_cart

# - - - - - - Add product in cart - - - - - - -

@app.route('/cart', methods=['POST'])
@cross_origin()
def my_cart():
    _id = request.json['_id']
    username = request.json['username']
    name = request.json['name']
    price = request.json['price']
    link = request.json['link']
    '''
    Idea: bấm vào nút giỏ hàng phía trên => gửi xuống db 
    Dưới db check == name => tăng sl 
    Dưới db check != name => thêm mới
    --------
    Phần giỏ hàng khi bấm xác nhận mua 
    => cập nhật lại giỏ hàng lần cuối
    '''
    arr = []
    check = mycol_cart.find_one({"_id": _id})
    if check is not None:
        # flag = 0
        for index in check['cart']:
            if index['name'] == name: # name là tên mặt hàng
                index['count'] += 1
                total = int(index['price'][1:])*int(index['count'])
                mycol_cart.update_one(
                    {"_id": _id, "cart.name": name},
                    {"$set": {"cart.$.count": index['count'], "cart.$.total": f'${total}'}}
                )
                return {
                "status": "success",
                "msg": "Your cart has been updated"
                }

        # Thêm mới do ko có tên sp (tồn tại username đã thêm hàng)
        list_cart = {"name": name, "count": 1, "price": price, "total": price,"link": link}
        mycol_cart.update_one(
            { '_id': _id },
            { '$addToSet': { "cart": list_cart } }
        )

        return {
                "status": "success",
                "msg": "Your cart has been updated"
            }

    # user chưa từng thêm hàng
    item_product = {'name': name, 'count': 1, 'price': price, 'total': price, 'link': link}
    arr.append(item_product)
    mycol_cart.insert_one({'_id': _id, 'username': username, 'cart': arr})

    return {
                "status": "success",
                "msg": "Your cart has been updated"
            }

# - - - - - - Update number item in cart - - - - - - -
@app.route('/count', methods=['POST'])
@cross_origin()
def update_number_cart():
    username = request.json['username']
    name = request.json['name']
    count = request.json['count']

    '''
    Idea: Thay đổi số lượng sp mua
    Nếu sl sp = 0 => Xóa sp hỏi giỏ hàng
    Nếu sl giỏ hàng trống => Xóa giỏ hàng của user đó khỏi Giỏ hàng
    '''
    cart = mycol_cart.find_one({"username": username})

    if cart is not None:
        if int(count) == 0:
            mycol_cart.update_one(
                {"username": username},
                {"$pull": {"cart": {"name": name}}}
            )
            cart = mycol_cart.find_one({"username": username})
            print(cart['cart'])
            if cart['cart'] == []:
                mycol_cart.delete_one({"username": username})
                money = 0
            else:
                money = 0
                cart = mycol_cart.find_one({"username": username})
                for t in cart['cart']:
                    money += int(t['total'][1:])

            return {
                "status": "Update number is successful!",
                "total": f'${money}'
            }

        for index in cart['cart']:
            if index['name'] == name:
                total = int(index['price'][1:])*int(count)
                # print(total)

        mycol_cart.update_one(
            {"username": username, "cart.name": name},
            {"$set": {"cart.$.count": int(count), "cart.$.total": f'${total}'}}
        )

        money = 0
        cart = mycol_cart.find_one({"username": username})
        for t in cart['cart']:
            money += int(t['total'][1:])

        return {
            "status": "Update number is successful!",
            "total": f'${money}'
        } 

    else:
        return {
            "status": "Not found your cart!"
        }
# ======================================================

# =========APPROVE-CUPON-CODE-&-CONFIRM-MONEY=========== 
@app.route('/order', methods=['POST'])
@cross_origin()
def cupon_and_money():
    username = request.json['username']
    cupon = request.json['cupon']

    if username == None:
        return {"status": "error",
                "msg": "Please input username!"}

    find_cart = mycol_cart.find_one({"username": str(username)})
    if find_cart == None:
        return {"status": "error",
                "msg": "Please choose product to order!"}
    money = 0
    for item in find_cart['cart']:
        money += int(item['total'][1:])

    if cupon is not None and str(cupon) == '#GENXSALE20':
        sale = round((money * 0.2),2)
        total = round((money - sale),2)
        mycol_cart.update_one(
            {"username": username},
            {"$set": {"origin": f'${money}', "discount": f'${sale}', "total": f'${total}'}}
        )
        return {"status": "success",
                "msg": "Your cupon code has been applied!",
                "origin": f'${money}',
                "cupon": f'${sale}',
                "total": f'${total}'}

    mycol_cart.update_one(
            {"username": username},
            {"$set": {"origin": f'${money}', "discount": "$0", "total": f'${money}'}}
        )

    return {"status": "success",
            "msg": "Cupon code is not used!",
            "origin": f'${money}',
            "cupon": '$0',
            "total": f'${money}'}


# ====================CONFIRM-ORDER===================== 
@app.route('/bill')
@cross_origin()
def bill():
    arr = []
    for x in mycol_bill.find({}):
        list_cart = {"_id": str(x['_id']), "username": x['username'], "email": x['email'], 'cart': x['cart'], "time": str(x['time']), "num_bill": x['num_bill'], "total": x['total']}
        arr.append(list_cart)
    return Response(json.dumps(arr),  mimetype='application/json')


@app.route('/send', methods=['POST'])
@cross_origin()
def confirm_order():
    username = request.json['username']

    find_email = mycol_user.find_one({"username": str(username)})
    email = find_email['email']

    find_cart = mycol_cart.find_one({"username": str(username)})
    if find_cart == None:
        return { "status": "error",
                "msg": "Please choose product to order!"}
    cart = find_cart['cart']

    if mycol_count.find_one({"count": "number of bill"}) == None:
        col_bill = {"_id": ObjectId(), "count": "number of bill", "number": 100}
        mycol_count.insert_one(col_bill)
        id_bill = 100
        
    else:
        find_bill = mycol_count.find_one({"count": "number of bill"})        
        id_bill = find_bill['number'] + 1 
        mycol_count.update_one(
            {"count": "number of bill"},
            {"$set": {"number": id_bill}}
        )
        print(id_bill)
        # return "true"
        
    # ----------time----------
    time = datetime.datetime.now()
    # ------------------------  
    
    # -----get-sum-money------ 
    money = find_cart['total']
    origin = find_cart['origin']
    discount = find_cart['discount']
    # ------------------------ 

    # Update collections bill
    bill = {"_id": ObjectId(), "username": username, "email": email, "cart": cart, "time": time, "num_bill": id_bill, "total": money}
    mycol_bill.insert_one(bill)

    # Send email
    output = render_template('bill.html', data=cart, username=username, email=email, date = time, bill = id_bill, total = money, origin=origin, discount=discount)
    send_mail(output, email, username)

    # Delete in cart
    mycol_cart.delete_one({"username": username})

    return{
        "status": "Comfirm order done!"
    }

# =====================================================

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port=5000)
    
# =====================================================

# ***********************************************************
'''
Referrences mongo:
- update: https://docs.mongodb.com/manual/reference/operator/update/positional/
- delete: https://docs.mongodb.com/manual/reference/operator/update/pull/
- add array: https://docs.mongodb.com/manual/reference/operator/update/addToSet/#mongodb-update-up.-addToSet
- sort descending: https://docs.mongodb.com/manual/reference/method/cursor.sort/
'''
# ***********************************************************
