from user import authentication
from transactions import journal
import banking
# from banking import reconciliation
# from banking.fvb import reconciliation as fvb
# from banking.ubsa import reconciliation as ubsa 
# from banking.online import reconciliation as online 
import sys

def checks():
    argument = len(sys.argv) - 1
    position = 1
    while (argument >= position):
        # print ("%i: %s" % (position, sys.argv[position]))
        print(sys.argv[position])
        position += 1
        



if __name__ == "__main__":
    checks()
    amount = 100
    authentication.authenticate_user()
    journal.receive_income(amount)
    journal.pay_expense(amount)
    # reconciliation.do_reconciliation()
    banking.fvb.reconciliation.do_reconciliation()
    # ubsa.do_reconciliation()
    # online.do_reconciliation()