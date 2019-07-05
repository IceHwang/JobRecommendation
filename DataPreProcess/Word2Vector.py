from DataPreProcess.FileIO import FileIO
class Word2Vector:
    @staticmethod
    def word2Vec(dpath, wordList):
        f = FileIO(dpath)
        classes = []
        skills = []
        skill = ''
        wordsList = []
        for word in wordList:
            w = word['name'] + ' '
            for i in word['skill']:
                w += i + ' '
            wordsList.append(w)
        for lines in wordsList:
            line = lines.split(' ')
            if len(line) != 1 and len(line) != 2:
                if line[0] not in classes:
                    classes.append(line[0])
                for i in range(1,len(line)):
                    if line[i] not in skills and line[i] != '\n' and line[i] != ' ' and line[i] != '':
                        skills.append(line[i])
                        skill += line[i] + ' '
        f.setSkillLine('skills.txt',skill)
        vectorList = []
        for lines in wordsList:
            line = lines.split(' ')
            if len(line) != 1 and len(line) != 2:
                vec = [0 for _ in range(len(skills))]
                for i in range(1,len(line)):
                    if line[i] in skills:
                        vec[skills.index((line[i]))] = 1
                l = str(classes.index(line[0])) + ' '
                for i in range(len(vec)):
                    l = l + str(i+1) + ':' + str(vec[i])+' '
                vectorList.append(l)
        f.setVectorList('vectorData.txt', vectorList)

