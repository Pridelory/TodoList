# TodoList-Springboot-backend-demo
## Preface

This is the back-end demo of the todo list appication. I spent 3 days writing the genral structure of the backend and the frontend. It mainly contains a registratoin module, a security validation module and the todolist module. At the same time, I used the idea of miroservices to make the project scalable.  

## Project Links

For Interface documents see http://34.105.255.229:8081/swagger-ui/index.html (1 month valid)

For related front-end documents see https://github.com/Pridelory/Todolist-vue-frontend

For live demo see http://34.105.255.229:8002/todolist/index.html (1 month valid)

## Tech Stacks

| Description     | Techs              | Description          | Techs        |
| --------------- | ------------------ | -------------------- | ------------ |
| Core Framework  | Spring, SpringBoot | Core Framework       | Mybatis-plus |
| Database(cache) | Redis              | Database(persistent) | MySQL        |
| Management      | Maven              | Front-end Framework  | Vue          |
| Deoloyment      | Linux              | Deployement-tool     | Docker       |

When sacling the project, it is easy to integrate the following tech stacks

| Description        | Techs           | Description            | Techs                |
| ------------------ | --------------- | ---------------------- | -------------------- |
| Core Framework     | SpringCloud     | Core Framework         | Mybatis-plus         |
| Service registrion | Nacos           | Gateway                | Spring Cloud Gateway |
| Distributed Lock   | Redis/Zookeeper | Distributed Tansaction | Seata                |
| Message Queue      | RabbitMQ        | Search                 | Elasticsearch        |
| Database           | MySQL           | Database               | MongoDB              |
| Database           | Cassendra       |                        |                      |



## System Design

Assume it has 1 million Daily active users, every user will post ten tasks as their todo list, and for the task, the analysis is as follows.

### Storage

#### **Task:**

id (5 Bytes) + topic (100 Bytes) + status (1 Byte) + description (500 Bytes)  + and time related fields (50 Bytes)  = 701 Bytes => using 1kB to represent the average capacity of a single task. 

So provided mainting this application for ten yeas, the total storage for task is as follows.

Total storage of store task for ten years = 10 * 365 * 1000,000 * 10 * 1kB = 285 TB

#### User:

Id(5 Bytes) + email(50 Bytes) + Password(200 Bytes) + status(1 Byte) + time related (50 Bytes) + something else(100KB) =406 Bytes => using 1KB to represent the average capacity of a user.

#### Sum:

Totol storeage = 285 TB * 2 = 570 TB



### User behavior analyse

When user uses the todolist app, they will always add (write), check (read) and tag(write), so this is an app with high concurrent reads and high concurrent writes. However, they mainly focus on the tasks in a given new day, so it has far low requirements for most of users to check the formal or completed tasks, so I design the system into two parts. The first part is cache, just put all the operations to the cache to improve the performance of the app. The second part is the database in the file system, not the memory, you know, the memory is very expensive. So in a given new day, just put all the data into redis or the mongodb or something else, and after that  day, it should have a unique thread to transfer data from cache to file system. 

### Requirements

#### Functional Requirements

1. User registration, login and logout
2. User adds, updates and delete tasks
3. User tags tasks to different status
4. User searches history tasks
5. Remind users of overdue tasks by email

#### Non-Functional Requirements

1. Improve performance
2. Make the app scalable
3. Make the app always available

### System Structure
