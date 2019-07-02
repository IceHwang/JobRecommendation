from DataPreProcess import DataCleanner, KeywordMapper
from DataPreProcess.FileIO import FileIO


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
    file = FileIO("../data/")
    # similiarWordList= file.getSimiliarWordList("similiarWord.txt")
    # file.setSimiliarWordList("similiarWord.txt",similiarWordList)
    # stopWordList = file.getSimiliarWordList("stopWordList.txt")
    # file.setStopWordList("similiarWord.txt",stopWordList)
    file.setUserDict('userDict.txt',['以太坊'])

    totalList=[]
    totalList=totalList+transformTest("算法工程师_data.json","算法工程师职位")
    totalList=totalList+transformTest("C_data.json","c职位")
    totalList=totalList+transformTest("Hadoop_data.json","hadoop职位")
    totalList=totalList+transformTest("Java_data.json","java职位")
    totalList=totalList+transformTest("PHP_data.json","php职位")
    totalList = totalList + transformTest("DBA_data.json", "DBA职位")
    totalList = totalList + transformTest("Flash_data.json", "Flash职位")
    totalList = totalList + transformTest("HTML5_data.json", "HTML5职位")
    totalList = totalList + transformTest("iOS_data.json", "iOS职位")
    totalList = totalList + transformTest("Node.js_data.json", "Node.js职位")
    totalList = totalList + transformTest("Python_data.json", "Python职位")
    totalList = totalList + transformTest("C++_data.json", "C++职位")
    totalList = totalList + transformTest("U3D_data.json", "U3D职位")
    totalList = totalList + transformTest("全栈工程师_data.json", "全栈工程师职位")
    totalList = totalList + transformTest("区块链_data.json", "区块链职位")
    totalList = totalList + transformTest("图像处理_data.json", "图像处理职位")
    totalList = totalList + transformTest("技术总监_data.json", "技术总监职位")
    totalList = totalList + transformTest("技术经理_data.json", "技术经理职位")
    totalList = totalList + transformTest("机器学习_data.json", "机器学习职位")
    totalList = totalList + transformTest("机器视觉_data.json", "机器视觉职位")
    totalList = totalList + transformTest("架构师_data.json", "架构师职位")
    totalList = totalList + transformTest("测试工程师_data.json", "测试工程师职位")
    totalList = totalList + transformTest("算法工程师_data.json", "算法工程师职位")
    totalList = totalList + transformTest("网络安全_data.json", "网络安全职位")
    totalList = totalList + transformTest("网络工程师_data.json", "网络工程师职位")
    totalList = totalList + transformTest("自然语言处理_data.json", "自然语言处理职位")
    totalList = totalList + transformTest("语音识别_data.json", "语音识别职位")
    totalList = totalList + transformTest("运维工程师_data.json", "运维工程师职位")

    file = FileIO("../data/")
    file.saveJsonList("test.txt",totalList)
    return






if __name__ == '__main__':
    main()
