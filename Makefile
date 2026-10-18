.PHONY: build run clean

build:
	javac Main.java

run: build
	java Main

clean:
	rm -f *.class