import DataCleanner
import KeywordMapper
from FileIO import FileIO


def main():
    file = FileIO("../data/")
    stopWordList=file.getStopWordList("stopWord.txt")
    similiarWordList=file.getSimiliarWordList("similiarWord.txt")
    javaJsonStringList=file.getJsonStringList("Java_data.json")
    jsonList=DataCleanner.DataCleanner.getCleanedJsonList("java",javaJsonStringList,stopWordList,similiarWordList)
    mylist=KeywordMapper.KeyWordMapper.transformJsonList(jsonList)
    file.saveJsonList("test.json",mylist)
    print(mylist)


if __name__ == '__main__':
    main()
