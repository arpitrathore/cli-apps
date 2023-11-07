# TODO cli

A cli based todo app

### Prerequisite JDK 21 and Graalvm

- Install [sdkman](https://sdkman.io/)
- Install jdk21 or higher along with graalvm using sdkman: `$ sdk use java 21-graalce`
- [Optional] Install [Quarkus cli](https://quarkus.io/guides/cli-tooling)

```sh
$ java -version
openjdk version "21" 2023-09-19
OpenJDK Runtime Environment GraalVM CE 21+35.1 (build 21+35-jvmci-23.1-b15)
OpenJDK 64-Bit Server VM GraalVM CE 21+35.1 (build 21+35-jvmci-23.1-b15, mixed mode, sharing)
```

### Build and install

```shell
# Using maven
mvn clean package -Pnative

# [Optional] Using quarkus cli
quarkus build --clean --native

# Install to $PATH
sudo cp ./target/todo-cli-1.0.0-SNAPSHOT-runner /usr/local/bin/todo
```

### Features

```txt
$ todo --help
Welcome to TODO App
Commands:
  ls, list              [--all (default), --todo🔴, --done🟢]
  add <todo>            Add a new task
  done <todo-id>        Mark a task as Done
  undone <todo-id>      Move a completed task back to TODO
  rm <todo-id>          Remove a task by its id
  clear                 Clear All tasks
  datadir               Print the path of data file
  raw                   Print the raw data file
```

### Usage

```txt
$ todo add Check jira status

$ todo add Schedule meeting with QA team

$ todo ls
+----+-------------------------------+--------+
| Id | Todo                          | Status |
+----+-------------------------------+--------+
| 1  | Check jira status             |   🔴   |
+----+-------------------------------+--------+
| 2  | Schedule meeting with QA team |   🔴   |
+----+-------------------------------+--------+

$ todo done 1

$ todo ls
+----+-------------------------------+--------+
| Id | Todo                          | Status |
+----+-------------------------------+--------+
| 1  | Check jira status             |   🟢   |
+----+-------------------------------+--------+
| 2  | Schedule meeting with QA team |   🔴   |
+----+-------------------------------+--------+

$ todo undone 1

$ todo ls
+----+-------------------------------+--------+
| Id | Todo                          | Status |
+----+-------------------------------+--------+
| 1  | Check jira status             |   🔴   |
+----+-------------------------------+--------+
| 2  | Schedule meeting with QA team |   🔴   |
+----+-------------------------------+--------+

$ todo rm 1

$ todo ls
+----+-------------------------------+--------+
| Id | Todo                          | Status |
+----+-------------------------------+--------+
| 2  | Schedule meeting with QA team |   🔴   |
+----+-------------------------------+--------+

$ todo add Review pull requests

$ todo ls
+----+-------------------------------+--------+
| Id | Todo                          | Status |
+----+-------------------------------+--------+
| 2  | Schedule meeting with QA team |   🔴   |
+----+-------------------------------+--------+
| 3  | Review pull requests          |   🔴   |
+----+-------------------------------+--------+

$ todo datadir
/Users/testuser/.arp/todo/data.json

$ todo raw
File path: /Users/testuser/.arp/todo/data.json
Data:
[
    {
        "id": 2,
        "message": "Schedule meeting with QA team",
        "status": "TODO"
    },
    {
        "id": 3,
        "message": "Review pull requests",
        "status": "TODO"
    }
]

$ todo clear
Are you sure you want to clear all TODOs? (y/n)
y

$ todo ls
+----+------+--------+
| Id | Todo | Status |
+----+------+--------+
+----+------+--------+
```
