#coding: utf-8
import sys
sys.path.append('Com_Api.src.main.java')

# imports the module from the given path
import TextAreaObserver

class Emoji(TextAreaObserver):

    def onType(self,emoji):

        if(emoji==":-)"):
            arr ="\uD83D\uDE0A"
            return arr



editor.registerHandler(Emoji())