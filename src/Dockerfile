FROM openjdk:17

WORKDIR /app

# Copy everything into the container
COPY . .

# Make sure the bin directory exists
RUN mkdir -p bin

# Compile all .java files under src/
RUN find src -name "*.java" > sources.txt \
    && javac -d bin @sources.txt

# Run the main class
CMD ["java", "-cp", "bin", "Main"]
