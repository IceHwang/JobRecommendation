from DataPreProcess import DataCleanner, KeywordMapper
from DataPreProcess.FileIO import FileIO


def transformTest(fileName,label):
    file = FileIO("../data/")

    stopWordList=file.getStopWordList("stopWord.txt")
    similiarWordList=file.getSimiliarWordList("similiarWord.txt")
    JsonStringList=file.getJsonStringList(fileName)
    jsonList=DataCleanner.DataCleanner.getCleanedJsonList(label,JsonStringList,stopWordList,similiarWordList)
    mylist=KeywordMapper.KeyWordMapper.transformJsonList(jsonList)
    print(mylist)
    return mylist


def main():
    file = FileIO("../data/")
    # similiarWordList= file.getSimiliarWordList("similiarWord.txt")
    # file.setSimiliarWordList("similiarWord.txt",similiarWordList)
    # stopWordList = file.getSimiliarWordList("stopWordList.txt")
    # file.setStopWordList("similiarWord.txt",stopWordList)

    totalList=[]
    totalList=totalList+transformTest("C_data.json","c")
    totalList=totalList+transformTest("C++_data.json","c++")
    totalList=totalList+transformTest("Hadoop_data.json","hadoop")
    totalList=totalList+transformTest("Java_data.json","java")
    totalList=totalList+transformTest("PHP_data.json","php")
    file = FileIO("../data/")
    file.saveJsonList("test.txt",totalList)
    return






if __name__ == '__main__':
    main()
