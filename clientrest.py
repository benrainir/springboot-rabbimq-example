
import requests
import json
ref_arquivo = open("./notificacoes.txt","r")

subscriptionMessage=""

for linha in ref_arquivo:
    linha = linha.rstrip(linha[-1])
    subscriptionMessage = linha.replace("'", '"')
    print('Subscription Globoplay API ',  subscriptionMessage)
    r = requests.post("http://localhost:8080/notify", subscriptionMessage,headers={'Content-type':'application/json', 'Accept':'application/json'})
    print(r.status_code, r.reason)

ref_arquivo.close()