# -*- coding:utf-8 -*-
import jieba.analyse
import json
import jieba.posseg
class KeyWordMapper:

    @staticmethod
    def transformJsonList(jsonList, userWordsDict=[]):
        fetch = {}
        for words in userWordsDict:
            jieba.add_word(words, tag='nz')
        for line in jsonList:
            data_desc = line['desc']
            data = data_desc
            for x, w in jieba.analyse.extract_tags(data, topK=80, withWeight=True, allowPOS=('nz', 'eng', 'un')):
                x = x.lower()
                if x in fetch.keys():
                    fetch[x] += 1
                else:
                    fetch[x] = 1

        lists = [x[0] for x in sorted(fetch.items(), key=lambda x: x[1], reverse=True)[:80]]
        Jsonlist = []
        for line in jsonList:
            data_desc = line['desc']
            data = data_desc
            skillList = []
            for x, w in jieba.analyse.extract_tags(data, topK=50, withWeight=True, allowPOS=('nz', 'eng', 'un')):
                x = x.lower()
                if x in lists:
                    skillList.append(x)
            Jsonlist.append({'name': line['name'],'skill': skillList})
        return Jsonlist










