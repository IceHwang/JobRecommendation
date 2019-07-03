from DataPreProcess import DataCleanner, KeywordMapper
from DataPreProcess.FileIO import FileIO
from DataPreProcess.Word2Vector import Word2Vector

def transformTest(fileName,label):
    file = FileIO("../data/")

    stopWordList=file.getStopWordList("stopWord.txt")
    similiarWordList=file.getSimiliarWordList("similiarWord.txt")
    userDict = file.getUserDict('userDict.txt')
    JsonStringList=file.getJsonStringList(fileName)
    jsonList=DataCleanner.DataCleanner.getCleanedJsonList(label,JsonStringList,stopWordList,similiarWordList)
    mylist=KeywordMapper.KeyWordMapper.transformJsonList(jsonList,userWordsDict=userDict)
    print(mylist)
    return mylist


def main():
    #file = FileIO("../data/")
    # similiarWordList= file.getSimiliarWordList("similiarWord.txt")
    # file.setSimiliarWordList("similiarWord.txt",similiarWordList)
    # stopWordList = file.getSimiliarWordList("stopWordList.txt")
    # file.setStopWordList("similiarWord.txt",stopWordList)
    #file.setUserDict('userDict.txt',['以太坊'])

    totalList=[]
    totalList=totalList+transformTest("算法工程师_data.json","算法工程师")
    totalList=totalList+transformTest("C_data.json","c/c++开发工程师")
    totalList = totalList + transformTest("Android_data.json", "Android开发工程师")
    totalList=totalList+transformTest("Hadoop_data.json","hadoop开发工程师")
    totalList=totalList+transformTest("Java_data.json","java开发工程师")
    totalList=totalList+transformTest("PHP_data.json","php开发工程师")
    totalList = totalList + transformTest("DBA_data.json", "数据库管理员")
    totalList = totalList + transformTest("Flash_data.json", "Flash动画师")
    totalList = totalList + transformTest("HTML5_data.json", "HTML5开发工程师")
    totalList = totalList + transformTest("iOS_data.json", "iOS开发工程师")
    #totalList = totalList + transformTest("Node.js_data.json", "Node.js开发工程师")
    totalList = totalList + transformTest("Python_data.json", "Python开发工程师")
    #totalList = totalList + transformTest("C++_data.json", "C++职位")
    totalList = totalList + transformTest("U3D_data.json", "U3D开发工程师")
    #totalList = totalList + transformTest("全栈工程师_data.json", "全栈工程师职位")
    totalList = totalList + transformTest("区块链_data.json", "区块链工程师")
    totalList = totalList + transformTest("图像处理_data.json", "图像处理算法工程师")
    totalList = totalList + transformTest("技术总监_data.json", "技术总监")
    totalList = totalList + transformTest("技术经理_data.json", "技术经理")
    totalList = totalList + transformTest("机器学习_data.json", "机器学习算法工程师")
    totalList = totalList + transformTest("机器视觉_data.json", "视觉算法工程师")
    totalList = totalList + transformTest("架构师_data.json", "架构师")
    totalList = totalList + transformTest("测试工程师_data.json", "测试工程师")
    totalList = totalList + transformTest("网络安全_data.json", "网络安全工程师")
    totalList = totalList + transformTest("网络工程师_data.json", "网络工程师")
    totalList = totalList + transformTest("自然语言处理_data.json", "自然语言处理工程师")
    #totalList = totalList + transformTest("语音识别_data.json", "语音识别职位")
    totalList = totalList + transformTest("运维工程师_data.json", "运维工程师")
    totalList = totalList + transformTest("深度学习_data.json", "深度学习算法工程师")

    file = FileIO("../data/")
    file.saveJsonList("test.txt",totalList)
    Word2Vector.word2Vec('../data/test.txt')
    return




if __name__ == '__main__':
    main()
