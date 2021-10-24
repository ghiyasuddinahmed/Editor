#coding: utf-8
import sys
sys.path.append('Com_Api.src.main.java')

# imports the module from the given path
import TextAreaObserver

class Emoji(TextAreaObserver):

    def onType(self,pos):

        emoji=editor.textArea.getText(pos-3,pos)

        if(emoji==":-)"):
            editor.textArea.replaceText(pos - 3, pos, editor.getEmoji())




editor.registerHandler(Emoji())