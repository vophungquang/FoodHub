import smtplib, ssl
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.header import Header
import constants as Const

def send_mail(html, email, username):
    sender_email = Const.HOST_MAIL
    receiver_email = str(email)
    password = Const.PASS_MAIL

    message = MIMEMultipart("mixed")
    message["Subject"] = "Your bill to pay at GenX Shopping"
    message["From"] = str(Header('GenX Shopping'))
    message["To"] = receiver_email

    # Create the plain-text and HTML version of your message
    text = f"""\
    Hi {username},
    Hope you enjoy your quality time with our services.
    Here is your bill for payment:
    """
    message_alternative = MIMEMultipart('alternative')
    message_related = MIMEMultipart('related')

    message_related.attach(MIMEText(html, 'html'))
    message_alternative.attach(MIMEText(text, 'plain'))
    message_alternative.attach(message_related)

    message.attach(message_alternative)

    context = ssl.create_default_context()
    with smtplib.SMTP_SSL("smtp.gmail.com", 465, context=context) as server:
        server.login(sender_email, password)
        server.sendmail(
            sender_email, receiver_email, message.as_string()
        )
