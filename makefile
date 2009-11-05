# Pretty general java makefile
# Copyright (c) Jasper Van der Jeugt

# The name of this file.
SELF="makefile"
# The main class.
MAIN="musique.Musique"
# Name for the jar archive.
JAR="Musique.jar"
# Documentation directory.
DOC="javadoc"
# Class path
CP="jdom.jar:DiscogsManager.jar:."

default:
	@# Find java files that have a newer timestamp
	@# than this file (this file always has the timestamp
	@# of the latest make invocation).
	@echo "Searching and compiling out-of-date .java files..."
	@find . \( -name '*.java' -a -newer $(SELF) \) | xargs --no-run-if-empty javac -cp $(CP)
	
	@# Update the timestamp for this file.
	@echo "Generating new timestamp..."
	@touch $(SELF)

tar:
	@rm -f Musique.tar.gz
	@find . -type f | xargs tar -czf Musique.tar.gz

clean:
	@echo "Removing .class files..."
	@find . -name '*.class' -exec rm '{}' \;
	@echo "Removing $(JAR)..."
	@rm -f $(JAR)
	@echo "Removing documentation..."
	@rm -rf $(DOC)/*

all:
	@echo "Searching and compiling all .java files..."
	@find . -name '*.java' | xargs --no-run-if-empty javac -cp ${CP}
	
run:
	@echo "Running main class..."
	@java -cp $(CP) $(MAIN)

jar:
	@echo "Creating java archive..."
	@find * \( \! -name '*.java' -a -type f \) | xargs jar cfe $(JAR) $(MAIN)

doc:
	@echo "Creating doc directory..."
	@mkdir -p $(DOC)
	@echo "Generating documentation..."
	@find . -name '*.java' | xargs javadoc -d $(DOC)

stats:
	@echo "Number of classes:"
	@find . -name '*.java' | wc -l | sed 's/^/    /'
	@echo "Lines of code:"
	@find . -name '*.java' | xargs wc -l | tail -n 1 | sed 's/[^0-9]//g' | sed 's/^/    /'
	@echo "Largest class file:"
	@find . -name '*.java' -exec wc -l '{}' \; | sort -rn | head -n 1 | sed 's/^/    /'
	@echo "Mean number of lines:"
	@find . -name '*.java' -exec wc -l '{}' \; | cut -f1 -d' ' | awk '{ sum += $$0 } END{ print sum/NR;}' | sed 's/^/    /'
