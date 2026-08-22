# Markdown_to_Word_Converter
Converts a Microsoft word document into a markdown file and vice versa

# Requirements
- (Java jdk 24 or higher)[https://www.oracle.com/anz/java/technologies/downloads/]
- (Pandoc)[https://github.com/jgm/pandoc/releases]
- (Apache Maven)[https://maven.apache.org/download.cgi]

# How to use
- Once the above requirements are installed, put the word document that needs to be converted to a markdown in (../src/main/resources/input) and name it **document.docx**.
- Next, open the project in an IDE and run the App.java file.
- The converted markdown document will be in the parent directory along with a copy of the word document as the one pasted in resources might not be usable.
- For a markdown to docx conversion, follow the same steps as above but with the markdown file named **document.md**.
