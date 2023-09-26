import requests

print ("[Module] online.Reconciliation loaded.")

def do_reconciliation():
    response = requests.get('https://www.wethinkcode.co.za')
    print("Doing Online Bank reconciliation.")
    print(response.status_code)