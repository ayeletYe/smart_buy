import sys
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from firebase_admin import auth

def print_help():
    print ("Usage: python users.py <operation>")
    print ("    List all users")
    print ("    --list")
    print
    print ("    Add a new user")
    print ("    --add <email> <password> <username> <first_name> <last_name> <city> [street]")
    print
    print ("    Delete existing user")
    print ("    --delete <email> <username>")
    sys.exit(1)

operation = None

if len(sys.argv) < 2:
    print_help()

if sys.argv[1] == "--list":
    operation = "list"
elif sys.argv[1] == "--add" and len(sys.argv) >= 8:
    operation = "add"
elif sys.argv[1] == "--delete" and len(sys.argv) == 4:
    operation = "delete"
else:
    print_help()

cred = credentials.Certificate("smartbuy-adminsdk.json")
firebase_admin.initialize_app(cred, {'databaseURL': 'https://smartbuy-3e990.firebaseio.com/'})

ref = db.reference('users')

if operation == "list":
    for username, user in ref.get().items():
        print("--   Username:   " + username)
        print("     Email:      " + user['Email'])
        print("     First Name: " + user['firstName'])
        print("     Last Name:  " + user['lastName'])
        print("     Address:    " + user['address'])
        print("     City:       " + user['city'])
        print

if operation == 'add':
    # <email> <password> <username> <first_name> <last_name> <city> [street]
    email = sys.argv[2]
    password = sys.argv[3]
    username = sys.argv[4]
    first_name = sys.argv[5]
    last_name = sys.argv[6]
    city = sys.argv[7]

    if len(sys.argv) > 8:
        street = sys.argv[8]
    else:
        street = '*UNKNOWN*'

    # check if username exists
    if username in ref.get():
        print("Username '" + username + "' is already registered!")
        sys.exit(1)

    # register to Auth
    auth.create_user(email=email, password=password)

    # Save into database
    ref.update({
        username: {
            'Email': email,
            'address': street,
            'city': city,
            'firstName': first_name,
            'lastName': last_name
        }
    })

if operation == 'delete':
    # <email> <username>
    email = sys.argv[2]
    username = sys.argv[3]

    # check if username exists
    if username not in ref.get():
        print("Username '" + username + "' doesn't exist!")
        sys.exit(1)

    # Delete from Auth by email
    user = auth.get_user_by_email(email)
    auth.delete_user(user.uid)

    # Delete from DB
    ref.child(username).delete()
