#!/usr/bin/env python3

def main():
    for i in range(10,100):
        with open('duplaszamok/'+str(i)+".txt",'w') as f:
            f.write("szoveg")

##############################################################################

if __name__ == "__main__":
    main()