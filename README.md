Возможные действия:
1) POST /bankaccount/{id} - Завести новый счет. На вход команда принимает
параметр номер счета - 5-ти значное число
2) PUT /bankaccount/{id}/deposit - Внести сумму на счёт. На вход команда принимает 2
параметра - номер счета и сумму к зачислению
3) PUT /bankaccount/{id}/withdraw - Снять сумму со счёта. На вход команда принимает
2 параметра - номер счета и сумму снятия
4) GET /bankaccount/{id}/balance - Узнать баланс. На вход команда принимает
параметр номер счета - 5-ти значное число
