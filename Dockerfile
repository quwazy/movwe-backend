# Ovo je multi-stage Docker build: https://docs.docker.com/build/building/multi-stage/
# Multi-stage build nam omogucava da koristimo jedan image koji ima sve alate
# da buildujemo aplikaciju, a drugi image kao osnovu za kranji image koji buildujemo.
#
# U prvom stage-u koristimo Maven image koji dolazi sa mavenom i Javom 17.
# Tu buildujemo aplikaciju i pravimo .jar fajl.
# U drugom stage-u koristimo OpenJDK image koji ima samo Javu sto nam je
# dovoljno da pokrenemo aplikaciju.
#
# Ovo je univerzalni Dockerfile koji moze da se koristi za sve Java/Spring mikroservise.

FROM maven:3.9.9 AS builder

# Ovaj deo definise HOME direktorijum koji se koristi kao radni direktorijum prilikom buildovanja.
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Ovaj deo kopira mikroservis u image kako bi Maven mogao da builduje taj mikroservis.
ADD . $HOME

# Ovaj deo builduje .jar fajl.
# - "--mount=type=cache,target=/root/.m2" sluzi da kesira maven dependencies
#   kako ih ne bi iznova i iznova preuzimali prilikom svakog buildovanja image-a
# - "-Drevision=$VERSION" - prosledjuje verziju koju smo definisali gore, ignorisati
# - "-Dmaven.test.skip" - iskljucuje pokretanje testova prilikom buildovanja image-a
# - "-f $HOME/pom.xml" - putanja do pom.xml fajla
RUN --mount=type=cache,target=/root/.m2 mvn -DfinalName=app -Dmaven.test.skip -f $HOME/pom.xml clean package

# Final stage
FROM alpine:latest

RUN apk add --no-cache openjdk21-jre

COPY --from=builder /usr/app/target/movwe-0.0.1.jar /movwe-0.0.1.jar

EXPOSE 9999

ENTRYPOINT ["java", "-jar", "/movwe-0.0.1.jar"]