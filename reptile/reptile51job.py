import re
import requests
from lxml import html
import time
import random
import json
from bs4 import BeautifulSoup

etree = html.etree
k = ['Hadoop', 'Python', 'Delphi',
         'VB', 'Perl', 'Ruby', 'Node.js', 'Go', 'ASP', 'Shell', '区块链', '后端开发', 'HTML5', 'Android', 'iOS',
         'WP', '移动开发', '前端开发', 'web前端', 'Flash', 'html5', 'JavaScript', 'U3D', 'COCOS2D-X', '前端开发', '深度学习',
         '机器学习', '图像处理', '图像识别', '语音识别', '机器视觉', '算法工程师', '自然语言处理', '测试', '测试工程师', '自动化测试', '功能测试', '性能测试', '测试开发',
         '游戏测试', '白盒测试', '灰盒测试', '黑盒测试', '手机测试', '硬件测试', '测试经理', '测试其它', '运维', '运维工程师', '运维开发工程师', '网络工程师', '系统工程师',
         'IT支持', 'IDC', 'CDN', 'F5', '系统管理员', '病毒分析', 'WEB安全', '网络安全', '系统安全', '运维经理', '运维其它', 'DBA', 'MySQL',
         'SQLServer', 'Oracle', 'DB2', 'MongoDB', 'ETL', 'Hive', '数据仓库', 'DBA其它', '高端职位', '技术经理', '技术总监', '架构师', 'CTO',
         '运维总监', '技术合伙人', '项目总监', '测试总监', '安全专家', '高端技术职位', '项目管理', '项目经理', '项目助理', '硬件开发', '硬件', '嵌入式', '自动化', '单片机',
         '电路设计', '驱动开发', '系统集成', 'FPGA开发', 'DSP开发', 'ARM开发', 'PCB工艺', '模具设计', '热传导', '材料工程师', '精益工程师', '射频工程师',
         '硬件开发', '企业软件', '实施工程师', '售前工程师', '售后工程师', 'BI工程师', '企业软件其它']

def parseInfo(url,key):
    # res = requests.get(url)
    # 移动适配
    # u = re.findall('<meta name="mobile-agent" content="format=html5;(.*?)">', res.text)
    #               <meta name="mobile-agent" content="format=html5;https://m.51job.com/search/jobdetail.php?jobid=109087803">

    headers = {
        'User-Agent': 'Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/ADR-1301071546) Presto/2.11.355 Version/12.10'
    }
    res = requests.get(url, headers=headers)
    res.encoding = 'utf-8'

    selector = etree.HTML(res.text)

    soup = BeautifulSoup(res.text,'lxml')

    title = selector.xpath('//*[@id="pageContent"]/div[1]/div[1]/p/text()')
    salary = selector.xpath('//*[@id="pageContent"]/div[1]/p/text()')
    company = selector.xpath('//*[@id="pageContent"]/div[2]/a[1]/p/text()')
    companyinfo = selector.xpath('//*[@id="pageContent"]/div[2]/a[1]/div/text()')
    companyplace = selector.xpath('//*[@id="pageContent"]/div[2]/a[2]/span/text()')
    place = selector.xpath('//*[@id="pageContent"]/div[1]/div[1]/em/text()')
    exp = selector.xpath('//*[@id="pageContent"]/div[1]/div[2]/span[2]/text()')
    edu = selector.xpath('//*[@id="pageContent"]/div[1]/div[2]/span[3]/text()')
    num = selector.xpath('//*[@id="pageContent"]/div[1]/div[2]/span[1]/text()')
    time = selector.xpath('//*[@id="pageContent"]/div[1]/div[1]/span/text()')
    info = soup.find('div', class_='ain')
    if info != None:
        info = info.text
    position = {
        'name': title,
        'city':companyplace,
        'company':company,
        'education':edu,
        'money':salary,
        'desc':info,
    }
    with open(key+'_data.json','a',encoding='utf-8') as f:
        f.write(json.dumps(position,ensure_ascii=False)+'\n')
    #print(position)


def getUrl(url,key):
    print('New page')
    res = requests.get(url)
    res.encoding = 'GBK'
    # print(res.text)
    if res.status_code == requests.codes.ok:
        selector = etree.HTML(res.text)
        urls = selector.xpath('//*[@id="resultList"]/div/p/span/a/@href')
        #                      //*[@id="resultList"]/div/p/span/a
        print(urls)
        for url in urls:
            parseInfo(url,key)
            time.sleep(0.5)


if __name__ == '__main__':
    for key in k:
        # 第一页
        url = 'https://search.51job.com/list/000000,000000,0000,00,9,99,' + key + ',2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare='
        getUrl(url,key)
        # 后页[2,100)
        urls = [
            'https://search.51job.com/list/000000,000000,0000,00,9,99,' + key + ',2,{}.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare='.format(
                i) for i in range(2, 50)]
        for url in urls:
            getUrl(url,key)

