# -*- coding:utf-8 -*-
import jieba.analyse
import json
import jieba.posseg
class KeyWordMapper:

    @staticmethod
    def transformJsonList(jsonList):
        fetch = {}
        for line in jsonList:
            data_desc = line['desc']
            data_desc = data_desc.split("\n")
            data = "".join(("".join(data_desc)).split("，"))
            for x, w in jieba.analyse.extract_tags(data, topK=50, withWeight=True, allowPOS=('nz', 'eng')):
                x = x.lower()
                if x in fetch.keys():
                    fetch[x] += 1
                else:
                    fetch[x] = 1

        lists = [x[0] for x in sorted(fetch.items(), key=lambda x: x[1], reverse=True)[:30]]
        print(lists)
        Jsonlist = []
        for line in jsonList:
            data_desc = line['desc']
            data_desc = data_desc.split("\n")
            data = "".join(("".join(data_desc)).split("，"))
            skillList = []
            for x, w in jieba.analyse.extract_tags(data, topK=50, withWeight=True, allowPOS=('nz', 'eng')):
                x = x.lower()
                if x in lists:
                    skillList.append(x)
            Jsonlist.append({'name': line['name'],'skill': skillList})
        return Jsonlist

def main():
    s = KeyWordMapper.transformJsonList([{"name": "C/C++开发实习生", "city": "厦门", "company": "网宿科技网宿厦门招聘", "education": "本科及以上", "money": "1k-2k", "desc": "职位描述：\n        \n        岗位职责：\n参与产品的开发和维护，完成从需求到设计、开发和上线等整个项目周期内的工作。\n\n任职条件：\n1、2018年毕业生，计算机相关专业优先，本科及以上学历，可实习2个月以上； 2、热爱计算机以及互联网技术，有良好的学习能力，热衷于解决挑战性的问题。\n技能要求：1、熟悉C/C++编程语言，熟练使用调试工具，并熟悉Perl，Python，Shell等脚本语言者优先；2、熟悉网络编程和多线程编程，对TCP/IP，HTTP等网络协议有一定理解，了解XML和HTML语言者优先；3、熟悉Linux操作系统者优先；4、有作为骨干参与项目开发经验者优先；5、参加过大学生数学建模竞赛、挑战杯、ACM程序设计竞赛者优先。"},
{"name": "C/C++系统研发实习生", "city": "苏州", "company": "思必驰科技研发三部招聘", "education": "硕士及以上", "money": "4k-5k", "desc": "职位描述：\n        \n        职位描述：1. C/C++系统平台开发与优化；\n\n\n任职资格：\n1. 计算机/软工/通信/电子信息等相关专业背景在校研究生；\n2. 学校不限，211及以上院校优先；\n3. 能全职实习4个月以上（公司鼓励实习生转正留下来）；\n4. 可以熟练使用C/C++编程,熟悉Linux环境开发及代码调试；\n5. 对操作系统与嵌入式了解的优先；\n\n实习期间表现合格者可转正。"},
{"name": "unity3d开发工程师（实习生）", "city": "武汉", "company": "灵图互动项目实施部招聘", "education": "大专及以上", "money": "3k-6k", "desc": "职位描述：\n        \n        岗位职责：\n1. 负责U3D项目开发；\n2. 根据需求文档编程实现相应功能；\n3. 与其他开发工程师及美术人员完成整个项目程序；\n4. Unity3D插件、脚本开发和维护；\n5. 负责新技术的研究与技术积累、关键技术的验证，并能服务于相关业务发展。\n\n岗位要求：\n1. 熟悉3D图形开发，有C、C++、C＃语言编程基础,；\n2. 熟悉Unity3D引擎架构开发；\n3. 较强的代码阅读能力，代码风格良好，能设计出高效，合理，易读，易扩展的程序结构；\n4. 良好的沟通能力和团队精神，做事认真、细致；\n5. 了解3DMAX建模，了解unity 3d与数据交互原理的优先考虑。\n6. 简历请附作品"},
{"name": "C++开发工程师（2019应届硕...", "city": "北京", "company": "北纬通信招聘", "education": "硕士及以上", "money": "10k-14k", "desc": "职位描述：\n        \n        此岗位仅接收2019硕士及以上学历岗位职责： 1、接触移动互联网、物联网最先进的技术和架构体系，参与应用平台的架构和开发；2、根据产品的需求，完成各种移动互联网项目的架构搭建和编码工作；3、与项目组成员配合进行模块间的联合调试；4、书写开发过程所需的技术文档；5、根据推广的要求，完成项目的性能改进，达到支持大访问量，大数据量的要求。岗位要求： 1、计算机或相关专业硕士及以上学历； 2、熟悉C++；熟悉大型数据库，及数据库语言； 3、具备大访问量，大数据量网站的开发能力；4、具有很强的事业心、责任感和质量意识，性格开朗随和，有较好的团队协作精神，较强的沟通能力。"},
{"name": "C/C++开发工程师（2019应届...", "city": "广州", "company": "广东亿迅招聘", "education": "本科及以上", "money": "7k-12k", "desc": "职位描述：\n        \n        本科及以上学历，计算机、软件、通信等相关专业\n任职资格:\n1、熟悉Unix/Linux操作系统下的C/C++开发，熟悉C++和面向对象技术；\n2、熟悉数据库开发、TCP/IP、HTTP协议等相关知识，理解常用算法、数据结构；\n3、对计算机编程技术有浓厚兴趣，有良好的编程风格和开发习惯；\n4、学习能力强，严谨踏实，具备良好的分析和解决问题能力；\n5、有较强的责任心和团队协作能力，善于沟通协作。"},
{"name": "2019-2020实习生--Java开发...", "city": "合肥", "company": "思特奇Si-tech招聘", "education": "学历不限", "money": "5k-8k", "desc": "职位描述：\n        \n        Java开发岗位职责：\n1、参与项目功能实现及模块开发；\n2、有机会参与改进系统的稳定性及易用性，提升用户体验；\n3、有机会参与前瞻技术的跟踪调研和产品创新。\n4、有机会参与Java核心产品功能和架构开发；\n\n岗位要求：\n1、2019-2020年应届毕业生，全日制一本及以上院校\n2、计算机相关专业，有java/c等语言基础，熟悉数据库相关知识；\n3、英语四级通过\n\n\n薪资福利：\n五险一金，周末双休，补充医疗保险，员工旅游，餐饮补贴，通讯补贴，交通补贴，专业培训，绩效奖金，弹性工作，定期体检，员工俱乐部活动……"},
{"name": "嵌入式软件工程师", "city": "广州", "company": "广州市奥威亚电子科技有限公司研发部招聘", "education": "本科及以上", "money": "8k-12k", "desc": "职位描述：\n        \n        1.统招本科及以上学历，通信/电子/计算机等相关专业，专业基础扎实（2020年毕业的需能接受大四全职实习）；\n2.主要工作方向是开发基于网络通信的嵌入式视音频编解码系统，应用层开发/网络编程/驱动开发，平台为SoC、DSP、ARM等，Linux操作系统；3.C/C++语言编程基础扎实，有志在软件领域长期发展；4.具备敬业精神、高度责任感和创新能力，富团队合作精神。\n\n我们的优势：\nA.专注信息化教育视频应用14年，深耕教育服务14载，将视音频技术融入到课堂录制，在线互动、课后点播、课例直播等各个教学环节；\nB.10,000余所中小学和高校成功案例，凭借优质的产品和方案，奥威亚的市场占有率领先同行，在全国各地先后打造万余项成功案例；\nC.占比40%的研发和技术团队，重视研发与创新，研发和技术团队占公司总人数的40%，近五年一直保持年收入12%的研发投入；\nD.33个本地化服务，全国建立33个服务网络，提供涵盖售前、售中、售后全过程的本地化服务，第一时间响应客户需求。\n \n我们的福利：\nA.富有竞争力的薪酬福利体系，根据个人表现提供年薪14月+；\nB.完整的社会保障体系，五险一金，周末双休，法定假日，带薪年假；\nC.丰富多彩的员工活动，倡导平缓工作与生活，月度生日会，员工聚餐，旅游活动等；\nD.免费提供工作餐，由公司农庄提供蔬菜和禽类，食材绿色健康；\nE.针对性一对一培养机制，良好工作氛围，提供多维度发展空间，体现个人价值；\nF.总部班车接送，远离拥堵公交(华师-公司、黄村-公司)。"},
{"name": "游戏服务器开发实习生", "city": "成都", "company": "tap4fun研发工作室招聘", "education": "本科及以上", "money": "6k-8k", "desc": "职位描述：\n        \n        岗位描述\n\n 深度参与手机游戏开发，与策划、客户端配合完成游戏功能需求的设计与实现\n 参与游戏公共业务模块的开发维护和升级\n 快速学习掌握相关技术，解决各种挑战性问题\n\n\n岗位要求\n\n 2020届计算机科学相关专业本科及以上学历，热爱游戏\n 扎实的计算机基础知识，深入理解数据结构、算法、操作系统等知识\n 精通至少一门静态类型编程语言（如C/C++/Java）及其思想\n 热爱学习，强烈的求知欲，强大的逻辑分析能力\n 抗压能力强，思维活跃，善于沟通交流，并具有高度责任感和团队合作精神\n\n具备以下技能会加分哦～\n\n 熟悉更多编程语言及其思想（如Erlang、Golang、Lua、Python、Ruby）\n 有过完整的服务器开发经历和作品\n 对游戏开发有极大的热情"},
{"name": "后端开发工程师（实习/PaaS）", "city": "北京", "company": "北森云计算招聘", "education": "本科及以上", "money": "3k-4k", "desc": "职位描述：\n        \n        工作职责:工作地点：北京、成都1、 负责北森人才管理Saas软件设计、开发工作；2、 解决工作中的技术难题，为用户提供高性能的体验。 3、 为产品设计提供优秀的建议。任职资格:1、热爱并执着于技术学习和实践，具有良好团队合作精神，有高度的责任心，有很强的学习能力；2、熟练掌握C#、ASP.NET、Java\"script\"、Ajax、Jquery等技术；3、熟练使用Visual Studio 2010&2012，熟悉SQL Server 2008 及存储过程的编写；4、熟悉UML、OO或有SaaS系统开发、产品设计或有大型项目设计开发实习经验者优先。5、计算机、软件相关专业，本科及以上学历，2020年毕业。"},
{"name": "C++开发工程师", "city": "上海", "company": "ZILLIZ研发招聘", "education": "本科及以上", "money": "13k-25k", "desc": "职位描述：\n        \n        工作职责：\n1、 负责高性能服务器端程序的设计，开发与测试；\n2、 数据库系统的设计，开发与测试。\n\n岗位要求：\n1、计算机相关专业本科及以上学历；\n2、熟悉Linux操作系统，具备丰富的C++开发经验，熟悉Shell/Python等脚本语言，熟悉设计模式和面向对象开发；\n3、精通各类算法和数据结构；\n4、有较强的独立解决问题能力、学习能力、以及沟通能力。\n\n有以下经历者优先考虑：\n1、对分布式系统、存储系统、并行计算、编译器、数据库内核、数据库引擎、进程调度、任务调度、中间件开发等任一方向有深入研究的优先考虑；\n2、有数据库存储引擎，执行引擎开发经验，熟悉数据库执行计划树优化；\n3、熟悉LLVM/LLVM JIT；\n4、有人工智能平台或大数据系统开发经验；\n5、曾在算法类竞赛中获奖；\n6、在计算机国际顶级会议相关领域发表过论文。"},
{"name": "游戏开发工程师（实习）", "city": "武汉", "company": "西山居游戏武汉西山居游戏招聘", "education": "本科及以上", "money": "5k-7k", "desc": "职位描述：\n        \n        岗位描述：\n    1、参与游戏原型讨论与设计，为不同平台创造有趣好玩的游戏\n    2、负责游戏功能的分析、设计、实现与测试，保证游戏系统的健壮与稳定\n    3、钻研游戏相关技术难题，跨越广阔的技术领域，解决各种挑战性问题\n岗位要求：\n    1、计算机或者相关专业，本科及以上学历，2020/2021毕业\n    2、扎实的计算机基础知识，深入理解数据结构、算法、计算机网络、图形学等技术\n    3、良好的逻辑综合分析能力和学习能力，具备良好的团队合作意识\n    4、热爱游戏，对游戏研发充满激情\n    5、至少掌握一种游戏开发常用的编程语言，具C++或C#编程语言经验优先\n    6、了解游戏开发流程，有游戏引擎（如Unity、Unreal等）使用经验者优先"},
{"name": "C++开发工程师（初级）", "city": "成都", "company": "盛趣时代招聘", "education": "大专及以上", "money": "5k-10k", "desc": "职位描述：\n        \n         工作内容：1、与其他相关人员配合，完成产品设计总体目标；2、根据产品任务分配，完成相应模块软件的设计、开发、编程任务。职位要求：1、一年及以上经验，大专及以上学历，计算机相关专业，有扎实的C++功底，理解OOP，能熟练使用STL、ATL…模板开发，有一定算法基础；2、熟悉windows下通讯机制，进程/线程管理，静态/动态库，有大型开源项目使用经历的加分；5、有了解浏览器前端技术(html5，ES标准，cache技术等)的加分；6、有掌握COM组件开发，IE相关组件知识的加分；7、有移动端(Android/IOS)APP开发经历或者熟悉framework的加分。"},
{"name": "软件测试实习生（2020届）", "city": "成都", "company": "新蛋MIS招聘", "education": "学历不限", "money": "2k-4k", "desc": "职位描述：\n        \n        职位描述： 工作职责：（欢迎热爱测试的2020届同学）1、具备良好的团队协作能力, 沟通表达能力, 理解能力.2、具备快速学习能力以及发现问题，解决问题的能力, 严谨的逻辑思维以及喜爱迎接各种挑战.3、 对计算机技术领域充满了较强求知欲和热情, 对业界技术的发展和进步具备良好地敏锐度和学习的欲望.4、 具备一定的计算机基础知识, 比如: 数据结构, 操作系统, 算法, 计算机网络, 数据库系统概论, 高数，线性代数，离散数学.5、 至少熟悉一种开发语言: C, C++, Java, C#, Python, Ruby等.6、 至少熟悉一种大型关系型数据库, SQL Server, Oracle, MySql等. 具备一定的SQL Script编写能力.岗位要求：1、公司为每一位实习生安排导师，并指定详细的实习培养计划；2、了解企业文化，相关制度规范 ；3、学习相关测试开发专业技能；4、进入团队，参与实际项目测试，编写用例，执行测试，协助完成自动化测试及一些测试工具；5、了解项目实施流程，熟悉团队合作方式。\n\n\n你将获得：\n公司为每一位实习生安排导师，并指定详细的实习培养计划；了解企业文化，相关制度规范 ；学习相关专业技能，包括行业业务，技术等；进入团队，参与实际项目，产出实际成果；了解项目实施流程，熟悉团队合作方式；公司可安排给予毕业设计方面的支持和指导；进行实习期考核和出具实习评价鉴定。"}
])
    print(s)

if __name__ == '__main__':
    main()








