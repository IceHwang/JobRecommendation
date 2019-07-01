# coding=utf8
import json


class DataCleanner():

    # @staticmethod
    # def getStopWordList():
    #     return ["tcl"]
    #
    # @staticmethod
    # def getSimiliarWordList():
    #
    #     return [{"word":"jsss", "similiarWordList":["javascript"]}]
    #
    # @staticmethod
    # def getFileStringList():
    #
    #     return [''' {"name": "JAVA开发工程师（应届生岗位）", "city": "惠州", "company": "TCL金融控股集团招聘", "education": "本科及以上", "money": "5k-10k", "desc": "职位描述：\n        \n        岗位职责：\n1. 负责互联网金融系统及应用研发，开展具体的系统设计以及编码、测试等工作；\n2. 根据项目有需要，能兼职从事应用测试工作，并组织业务线人员开展验收测试；\n3. 在应用投入运行阶段，为业务系统运行提供必要的技术支持。\n任职要求：\n1、全日制本科院校\n2、积极上进，勤学好问，自驱力强，勇于挑战\n3、认真负责，热爱IT行业。熟悉UNIX/Linux操作系统、熟练掌握Java编程、SQL等相关技术。"}''',
    #             '''{"name": "java高级开发工程师", "city": "北京", "company": "京东招聘", "education": "本科及以上", "money": "19k-30k", "desc": "职位描述：\n        \n        职位描述\n1、 负责后台系统的研发，及时解决项目涉及到的技术问题。\n2、 参与系统需求分析与设计，负责完成核心代码编写，接口规范制定，架构设计。\n3、 参与后台服务性能优化，所开发的服务能够满足千万到亿级的pv访问量。\n任职要求\n1、3年以上软件开发经验，精通Java语言，熟悉Linux操作系统，熟悉maven等版本管理工具软件；\n2、熟悉mysql，对其常用知识点的原理很清楚，熟悉数据库性能优化常识；\n3、熟悉Spring/Springmvc, Mybatis等常用框架，阅读过Spring等框架源码优先；\n4、熟悉Redis、mq、zk、dubbo、es、MangoDB、javascript、Ajax等常用技术；\n5、良好的沟通能力、团队合作精神；认真负责、具有高度责任感；优秀的学习能力；\n6、计算机软件/应用相关专业本科（及以上）学历；\n7、有供应链、电子商务平台工作经验者优先。"}''',
    #             '''{"name": "高级java开发工程师", "city": "武汉", "company": "方正璞华研发部招聘", "education": "本科及以上", "money": "10k-19k", "desc": "职位描述：\n        \n        任职资格：\n1、5年以上JAVA开发经验；\n2、精通J2EE技术体系，熟练使用Spring boot、SpringMVC+Mybatis等开源框架并了解工作原理；\n3、精通JQuery、JavaScript/Ajax、Bootstrap等前端技术；熟悉SVN、Maven、Junit工具；\n4、精通Oracle、MySql数据库；熟悉Liunx操作系统下的操作命令；\n5、有需求分析、项目管理经验者优先；\n6、有良好的执行力及沟通能力，工作主动，善于思考，具备团队协作精神。\n岗位职责：\n1、参与业务需求分析；\n2、进行软件、系统的概要设计和详细设计；\n3、负责编码实现，确保产品的质量、安全和性能；\n4、维护升级优化现有产品，快速定位并修复现有软件缺陷等；\n5、编写部署方案，负责产品的更新发布；\n6、其他领导交办的工作。"}'''
    #             ]

    @staticmethod
    def getCleanedJsonList(label,jsonStringList,stopWordList,similiarWordList):
        label=label.lower()
        cleanedJsonList=[]
        for jsonString in jsonStringList:
            jsonString = jsonString.lower()
            for stopWord in stopWordList:
                jsonString=jsonString.replace(stopWord,"")
            for similiarWord in similiarWordList:
                for targetWord in similiarWord["similiarWordList"]:
                    word=similiarWord["word"]
                    jsonString = jsonString.replace(targetWord, word)
            if not jsonString.find(label):
                continue
            myJson = json.loads(jsonString,strict=False)
            myJson["name"]=label
            if myJson["desc"]=="" or myJson["desc"]== None:
                continue
            cleanedJsonList.append(myJson)
        return cleanedJsonList


def main():
    label="java"
    jsonStringList=DataCleanner.getFileStringList()
    stopWordList=DataCleanner.getStopWordList()
    similiarWordList=DataCleanner.getSimiliarWordList()
    cleanedJsonList=DataCleanner.getCleanedJsonList(label,jsonStringList,stopWordList,similiarWordList)
    print(cleanedJsonList)

if __name__ == '__main__':
    main()
