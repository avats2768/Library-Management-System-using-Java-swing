# Use OpenJDK 17
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Compile Java files
RUN javac -d bin src/MyLibrary/DbConnection.java src/Main.java

# Run your main class
CMD ["java", "-cp", "bin", "Main"]
