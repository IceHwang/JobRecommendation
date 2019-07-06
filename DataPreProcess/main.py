from DataPreProcess import DataCleanner, KeywordMapper
from DataPreProcess.FileIO import FileIO
from DataPreProcess.Word2Vector import Word2Vector
import os
def transformTest(fileName,label):
    file = FileIO("../data/others/")

    stopWordList=file.getStopWordList("stopWord.txt")
    similiarWordList=file.getSimiliarWordList("similiarWord.txt")
    userDict = file.getUserDict('userDict.txt')
    file = FileIO("../data/others/ReptilianData/")
    JsonStringList=file.getJsonStringList(fileName)
    jsonList=DataCleanner.DataCleanner.getCleanedJsonList(label,JsonStringList,stopWordList,similiarWordList)
    mylist=KeywordMapper.KeyWordMapper.transformJsonList(jsonList, userWordsDict=userDict)
    print(mylist)
    return mylist


def main():
    totalList = []
    jobs = 'android开发工程师 C#开发工程师 c/c++开发工程师 数据库管理员 flash动画师 hadoop开发工程师 html5开发工程师 ios开发工程师 java开发工程师 php开发工程师 python开发工程师 u3d开发工程师 区块链工程师 图像处理算法工程师 嵌入式软件开发工程师 技术总监 技术经理 视觉算法工程师 架构师 测试工程师 深度学习算法工程师 网络安全工程师 网络工程师 自然语言处理工程师 运维工程师'
    job = jobs.split(' ')
    filename = os.listdir(r'../data/others/ReptilianData')
    for i in range(len(filename)):
        totalList += transformTest(filename[i], job[i])
    file = FileIO("../data/2019_0705_1601/cleanData/")
    file.saveJsonList("data.txt",totalList)
    Word2Vector.word2Vec('../data/2019_0705_1601/VectorData/', totalList)
    return

if __name__ == '__main__':
    main()
