FROM openjdk:17

WORKDIR /app

# Copy all the files into the container (including src and any other necessary files)
COPY . .

# Make sure the bin directory exists for compiled classes
RUN mkdir -p bin

# Compile all .java files from the src directory and output them into the bin directory
RUN find src -name "*.java" > sources.txt && javac -d bin @sources.txt

# If your Main.java is part of a package, use the fully qualified name like com.example.Main
CMD ["java", "-cp", "bin", "Main"]  # Update "Main" if it's in a package (e.g., "com.example.Main")
