class FileIO():
    def __init__(self,dpath):
        self.dpath = dpath

    def getJsonStringList(self,filename):
        #jsonStringList = []
        with open(self.dpath+filename, 'r', encoding='utf-8') as f:
            jsonStringList = [lines for lines in f.readlines()]
        return jsonStringList

    def setStopWordList(self, filename, stopWordList):
        with open(self.dpath+filename, 'w', encoding='utf-8') as f:
            for line in stopWordList:
                f.write(line+'\n')
        return

    def getStopWordList(self, filename):
        stopWord = []
        with open(self.dpath+filename, 'r', encoding='utf-8') as f:
            for line in f.readlines():
                line=line.replace("\n","")
                stopWord.append(line)

        return stopWord

    def setSimiliarWordList(self, filename, similiarWordList):
        with open(self.dpath+filename, 'w', encoding='utf-8') as f:
            for similiarWord in similiarWordList:
                f.write(similiarWord['word']+':')
                for i in similiarWord['similiarWordList']:
                    f.write(i+' ')
                f.write('\n')
        return

    def getSimiliarWordList(self, filename):
        SimiliarWord = []
        with open(self.dpath + filename, 'r', encoding='utf-8') as f:
            for lines in f.readlines():
                if lines != '' and lines != '\n':
                    word = {}
                    word['word'] = lines.split(':')[0]
                    similiarWordList = lines.split(':')[1].split(' ')
                    if '\n' in similiarWordList:
                        similiarWordList = similiarWordList[:len(similiarWordList)-1]
                    word['similiarWordList'] = similiarWordList
                    SimiliarWord.append(word)

        return SimiliarWord
    def saveJsonList(self,filename, JsonList):
        with open(self.dpath+filename, 'w', encoding='utf-8') as f:
            for i in JsonList:
                f.write(i['name']+' ')
                for j in i['skill']:
                    f.write(j+' ')
                f.write('\n')

    def setUserDict(self, filename, dictList):
        with open(self.dpath+filename, 'w', encoding='utf-8') as f:
            for i in dictList:
                f.write(i)
                f.write('\n')

    def getUserDict(self, filename):
        words = ""
        with open(self.dpath+filename, 'r', encoding='utf-8') as f:
            for line in f.readlines():
                words = words + line
        return words.split('\n')[:-1]

    def setVectorList(self, filename, VectorList):
        with open(self.dpath+filename, 'w', encoding='utf-8') as f:
            for vector in VectorList:
                f.write(vector+'\n')

    def getWordList(self, filename):
        wordList = []
        with open(self.dpath+filename, 'r', encoding='utf-8') as f:
            for lines in f.readlines():
                wordList.append(lines)
        return wordList

    def setSkillLine(self,filename,skills):
        with open(self.dpath+filename, 'w', encoding='utf-8') as f:
            f.write(skills)

    def getJobList(self,filename):
        jobList = []
        with open(self.dpath+filename,'r',encoding='utf-8') as f:
            for line in f.readlines():
                line = line.split(' ')
                if line[0] not in jobList:
                    jobList.append(line[0])
        return jobList







