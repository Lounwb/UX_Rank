import json
import logging
import time
import numpy as np
import sys
sys.path.append('..')
import utils

import log

class JSONData:
    def __init__(self, file_path=None):
        
        self.file_path = file_path
        self.json_data = parser_json(file_path)

        self.data = self._get_attirbute('data')
        self.message = self._get_attirbute('message')
        self.meta = self._get_attirbute('meta')

        self.performanceAttrs = self._get_item('performanceAttr', utils.performance_mapping)
        self.interactionAttrs = self._get_item('interactionAttr', utils.interaction_mapping)

        self.desc = self._get_attirbute('desc')
        self.deviceGroupId = self._get_attirbute('deviceGroupId')

        self.envAttr = self._get_attirbute('envAttr')
        self.eventId = self._get_attirbute('eventId')
        self.eventName = self._get_attirbute('eventName')
        self.eventTypeId = self._get_attirbute('eventTypeId')
        self.projectId = self._get_attirbute('projectId')

        self.screenDirect = self._get_attirbute('screenDirect')
        self.sessionId = self._get_attirbute('sessionId')
        self.userId = self._get_attirbute('userId')

    def __len__(self):
        return len(self.json_data)
    
    def _get_item(self, _name: str, mapping):
        _list = []
        _tmp_list = {}
        for d in self.data:
            _tmp_list.clear()
            for key in d[_name].keys():
                place = mapping[key]
                # v: specific value of attrs
                # value: value after transforms
                v = d[_name][key]['value']
                if isinstance(v, str):
                    # TODO the function need to be implemented
                    if 'True' not in v and 'False':
                        if v == 'Good':
                            value = 90
                        elif v == 'Needs Improvement':
                            value = 50
                        else:
                            value = 0
                    else:
                        if v == 'True':
                            value = 1
                        else:
                            value = 0
                else:
                    value = v
                _tmp_list[place] = value
            _list.append(dict_to_array(_tmp_list))
        _list = np.array(_list)
        return _list
    def _get_attirbute(self, _attri: str):
        try:
            attribute = self.json_data[_attri]
            return attribute
        except KeyError:
            # logging.error(f'{time.asctime()} [JSON_PARSER FAILED] {_attri} KEY ERROR')
            return ''

def dict_to_array(dict):
    array = np.zeros(22)
    # TODO missing places of items
    for key, value in dict.items():
        array[key] = value
    return array
def parser_json(file_path):
    try:
        with open(file_path, encoding='utf-8') as f:
            json_file = f.read()
        json_data = json.loads(json_file)
        return json_data
    except FileNotFoundError:
        log.log_error(error='[JSON_PARSER FAILED]', reason=f'{file_path} NOT FOUND')