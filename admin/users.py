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
    print ("    --add <email> <password> <is_admin_0/1> <username> <first_name> <last_name> <city> [street]")
    print
    print ("    Delete existing user")
    print ("    --delete <email>")
    sys.exit(1)

operation = None

if len(sys.argv) < 2:
    print_help()

if sys.argv[1] == "--list":
    operation = "list"
elif sys.argv[1] == "--add" and len(sys.argv) >= 9:
    operation = "add"
elif sys.argv[1] == "--delete" and len(sys.argv) == 3:
    operation = "delete"
else:
    print_help()

cred = credentials.Certificate("smartbuy-adminsdk.json")
firebase_admin.initialize_app(cred, {'databaseURL': 'https://smartbuy-3e990.firebaseio.com/'})

ref = db.reference('users')

if operation == "list":
    for uid, user in ref.get().items():
        print("--   Email:      " + user['email'])
        print("     Username:   " + user['userName'])
        print("     First Name: " + user['firstName'])
        print("     Last Name:  " + user['lastName'])
        print("     Address:    " + user['address'])
        print("     City:       " + user['city'])
        print("     isAdmin:    " + str(user['admin']))
        print("     isPhoto:    " + str(user['photo']))
        print

if operation == 'add':
    # <email> <password> <is_admin> <username> <first_name> <last_name> <city> [street]
    email = sys.argv[2]
    password = sys.argv[3]
    is_admin = sys.argv[4] == "1"
    username = sys.argv[5]
    first_name = sys.argv[6]
    last_name = sys.argv[7]
    city = sys.argv[8]

    if len(sys.argv) > 9:
        street = sys.argv[9]
    else:
        street = '*UNKNOWN*'

    # register to Auth
    try:
        user = auth.create_user(email=email, password=password)
    except Exception as e:
        print ("Failed to add user!")
        print (e)
        sys.exit(1)

    uid = user.uid

    # Save into database
    ref.update({
        uid: {
            'email': email,
            'admin': is_admin,
            'userName': username,
            'firstName': first_name,
            'lastName': last_name,
            'city': city,
            'address': street,
            'photo': False,
        }
    })

if operation == 'delete':
    # <email>
    email = sys.argv[2]

    # Get user from Auth
    try:
        user = auth.get_user_by_email(email)
    except Exception as e:
        print ("Failed to get user!")
        print (e)
        sys.exit(1)

    # Delete from DB
    ref.child(user.uid).delete()

    # Delete from Auth
    auth.delete_user(user.uid)

