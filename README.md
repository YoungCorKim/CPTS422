# CheckStyleTestPackage

Project for CPTS422 just copied same name so i would not mess up the organization of files
##  Included Checks

### 1. `NumberOfLoopsCheck`

 Description:  
Detects and counts loop statements (`for`, `while`, `do-while`) in the source code.

 How it works:
- Resets the loop counter at the start of each file (`beginTree`)
- Increments the counter for each loop token encountered (`visitToken`)
- Logs a message for each loop statement with its line number
- Logs the total number of loops at the end of the file (`finishTree`)

 Tokens Monitored:
- `TokenTypes.LITERAL_FOR`
- `TokenTypes.LITERAL_WHILE`
- `TokenTypes.LITERAL_DO`

---

### 2. `NumberOfCommentsCheck`

Description: 
Detects and counts all comments in the source code, including single-line (`//`) and block (`/* */`) comments.

How it Works:
- Resets the comment counter at the start of each file (`beginTree`)
- Increments the counter for each comment token encountered (`visitToken`)
- Logs a message for each comment with its line number
- Logs the total number of comments at the end of the file (`finishTree`)


Tokens Monitored:
- `TokenTypes.SINGLE_LINE_COMMENT`
- `TokenTypes.BLOCK_COMMENT_BEGIN`

