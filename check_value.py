import re 
import hashlib

def check_username(username):

    if len(username) < 6:
        return 'Length username should be at least 6'
          
    if len(username) > 20:
        return 'Length username should be not be greater than 15'
    
    return username

def check_email(email):
    regex = '^(\w|\.|\_|\-)+[@](\w|\_|\-|\.)+[.]\w{2,3}$'

    if(re.search(regex, email)):
        return email
        
    else:
        return "Invalid Email"

def check_password(passwd):
      
    SpecialSym =['$', '@', '#', '%', '|', '!', '^', '&', '*', '/', '?', '=', '+', '-', '_', '.', ',', ';', ':',]
      
    if len(passwd) < 6:
        return 'Length password should be at least 6'
          
    if len(passwd) > 20:
        return 'Length password should be not be greater than 15'
          
    if not any(char.isdigit() for char in passwd):
        return 'Password should have at least one numeral'
          
    if not any(char.isupper() for char in passwd):
        return 'Password should have at least one uppercase letter'
          
    if not any(char.islower() for char in passwd):
        return 'Password should have at least one lowercase letter'
          
    if not any(char in SpecialSym for char in passwd):
        return 'Password should have at least one of the symbols $@#?$^...'
    
    return passwd

def hash_password(password):
    result = hashlib.md5(password.encode())
    return result.hexdigest()
