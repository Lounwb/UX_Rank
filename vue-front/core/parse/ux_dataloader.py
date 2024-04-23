from json_parser import JSONData
import os
import numpy as np

class UXDataLoader:
    def __init__(self, file_path=None) -> None:
        self.file_path = file_path
        self.data = self._load()
    def __len__(self):
        pass
    def _get_item(self, _name: str):

        pass
    def __iter__(self):
        pass
    def __next__(self):
        pass
    def _load(self):
        _list = []
        dirs = os.listdir(self.file_path)
        # WARNING maybe _tmp_list have different shapes. Normally, it has the shape of (3, 19(*), 22)
        for dir in dirs:
            json = JSONData(os.path.join(self.file_path, dir))
            _tmp_list = []
            _tmp_list.append(json.interactionAttrs)
            _tmp_list.append(json.performanceAttrs)
            _tmp_list = np.array(_tmp_list)
            _list.append(_tmp_list)
        return _list
