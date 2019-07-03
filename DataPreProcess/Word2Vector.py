class Word2Vector:
    @staticmethod
    def word2Vec(dataPath):
        skills = []
        classes = []
        with open(dataPath,'r',encoding='utf-8') as f:
            for line in f.readlines():
                l = line.split(' ')
                if l[0] not in classes:
                    classes.append(l[0])
                for i in range(1,len(l)):
                    if l[i] not in skills and l[i] != '\n':
                        skills.append(l[i])
        with open('../data/input/skills.txt','w',encoding='utf-8') as f:
            for skill in skills:
                f.write(skill+' ')

        with open('../data/input/data.txt', 'w', encoding='utf-8') as f:
            with open(dataPath, 'r', encoding='utf-8') as f1:
                for line in f1.readlines():
                    l = line.split(' ')
                    vec = [0 for _ in range(len(skills))]
                    for i in range(1, len(l)):
                        if l[i] in skills:
                            vec[skills.index(l[i])] = 1
                    f.write(str(classes.index(l[0]))+' ')
                    for i in range(len(vec)):
                        f.write(str(i+1)+':'+str(vec[i])+' ')
                    f.write('\n')
if __name__ == '__main__':
    Word2Vector.word2Vec('../data/test.txt')
