import re
import requests
from lxml import html
import time
import random
import json
from bs4 import BeautifulSoup

etree = html.etree
k = [ 'PHP','技术经理', '技术总监', '架构师', 'CTO',
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
    with open('../data/'+key+'_data.json','a',encoding='utf-8') as f:
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
        try:
            url = 'https://search.51job.com/list/000000,000000,0000,00,9,99,' + key + ',2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare='
            getUrl(url, key)
            urls = [
                'https://search.51job.com/list/000000,000000,0000,00,9,99,' + key + ',2,{}.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare='.format(
                    i) for i in range(2, 20)]
            for i in range(len(urls)):
                getUrl(urls[i], key)
        except Exception as e:
            print(e)


