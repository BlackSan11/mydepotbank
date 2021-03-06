# Что это?
Программа представляет собой реализацию REST API, для производста простых операций с банковскими счетами.

# Конфигурирование, соборка и развертывание
Пред сборкой, в файле /src/main/resources/application.properties необходимо прописать учетные данные для доступа к БД PostgresSQL, либо использовать уже имеющиеся.
Для сборки, в коталоге проекта, необходимо выполнить:
```bash
mvn package
```
После чего, в каталоге /target/ будет собран и создан файл accoperator-0.0.1-SNAPSHOT.jar, для запуска используйте команду:
```bash
java -jar accoperator-0.0.1-SNAPSHOT.jar
```

# Методы API:
### 1) POST /bankaccount/{id} - Завести новый счет.  
  #### Параметры:  
  >'id' - номер счета, 5-ти значное число.  
  #### При успешном выполнении программа вернет - "ОК".  
  
### 2) PUT /bankaccount/{id}/deposit - Внести сумму на счёт.  
  #### Параметры:  
   >'id' - номер счета, 5-ти значное число.  
   >'sum' - сумма внесения, положительное число, передается в теле запроса.  
  #### При успешном выполнении программа вернет - "ОК".  
  
### 3) PUT /bankaccount/{id}/withdraw - Снять сумму со счёта.  
  #### Параметры:  
   >'id' - номер счета, 5-ти значное число.  
   >'sum' - сумма снятия, положительное число, передается в теле запроса. 
  #### При успешном выполнении программа вернет - "ОК".  
  
### 4) GET /bankaccount/{id}/balance - Узнать баланс.  
  #### Параметры:  
   >'id' - номер счета, 5-ти значное число.  
  #### При успешном выполнении программа вернет сумму на балансе выбранного счета.  
  
  # Возвращаемые ошибки API:
  ERROR:Account with this 'id' already exist - счет с данным номером уже зарегистрирован.  
  ERROR:The length of the 'id' needs to be 5 characters - длинна номера счета дожна быть равна 5-ти.  
  ERROR:Account with this 'id' does not exist - счет с данным номером не зарегистрирован.  
  ERROR:Param 'sum' should not be negative - сумма пополнения/снятия не может быть отрицательной.  
  ERROR:There is no such amount on the balance - на балансе недостаточно денег для снятия.  
  ERROR:Failed to convert value of type... - неверный тип данных передан в параметре.  
  ERROR:Request method '*' not supported - неверный тип запроса.  
  
  # БД
Для создания таблицы в вашей БД, выполните следующий SQL в вашей СУБД
```
create table if not exists accounts
(
	id bigserial not null
		constraint accounts_pk
			primary key,
	balance numeric
)
;

create unique index if not exists accounts_id_uindex
	on accounts (id)
;
```
