# MuRa Web Interface
[![](https://github.com/ZhongXiLu/MuRa-Web/workflows/Gradle%20CI/badge.svg)](https://github.com/ZhongXiLu/MuRa-Web/actions?query=workflow%3A%22Gradle+CI%22) [![](https://github.com/ZhongXiLu/MuRa-Web/workflows/Docker%20CI/badge.svg)](https://github.com/ZhongXiLu/MuRa-Web/actions?query=workflow%3A%22Docker+CI%22)

User-friendly web interface for [MuRa](https://github.com/ZhongXiLu/MuRa). It also provides additional features such as applying mutation testing automatically and having an option to use optimal weights when ranking the mutants.

**Important**: this web interface only works with Maven projects using JUnit4.

## How to use

### Docker

1. Pull the docker image from Docker Hub and run the server locally:
```bash
docker pull zhongxilu/mura-web:latest
docker run --name mura-web -d -p 8080:8080 zhongxilu/mura-web:latest
```

Alternatively, build and run the docker image yourself:
```bash
gradle build
docker build -t zhongxilu/mura-web  .
docker run --name mura-web -t -p 8080:8080 zhongxilu/mura-web:latest
```
2. Visit http://localhost:8080/

### Gradle

1. Create a PostrgeSQL user with username `postgres` and password `postgres` and a database called `mura-web`
2. `gradle bootRun`
3. Visit http://localhost:8080/

## Screenshots

![](imgs/screenshot1.png)
---
![](imgs/screenshot2.png)
