import subprocess
import os
from datetime import datetime
from uuid import uuid4
import shutil
import json
import sys

from run.reporter import *
from log import *

BASE_TIMINGS = [
    'first-contentful-paint',
    'speed-index',
    'interactive',
    'first-meaningful-paint',
    'first-cpu-idle',
    'estimated-input-latency',
    'time-to-first-byte',
]

class LighthouseRunner:
    def __init__(self, url='', preset='desktop', quiet=True,
                 additional_settings=[], timings=BASE_TIMINGS, 
                 output_type='both', output_dir='./tmp'):
        
        self.timings = timings
        self.output_dir = output_dir
        self.output_type = output_type

        self._run(url, preset, additional_settings)
        self.report = self._get_report()

        self._clean()

    def _run(self, url='', preset='desktop',
                 additional_settings=[]):
        try:
            command = [
                'lighthouse',
                url,
                '--preset={0}'.format(preset),
                '--output=json,html'
            ]
            command = command + additional_settings
            command = ' '.join(command)
            subprocess.check_call(command, shell=True)
        except subprocess.CalledProcessError as e:
            # If an error occurs, raise a RuntimeError with a detailed error message
            log_error(error='[CalledProcessError]', reason=f'RETURN CODE: {e.returncode}')
        except RuntimeError as se:
            log_error(error='[RuntimeError]', reason=f'RETURN: {se}')
        
    def _get_report(self):
        folder_path = os.getcwd()
        files = os.listdir(folder_path)
        for file in files:
            if file.endswith('html') or file.endswith('json'):
                output_dir_name = file.split('.report')[0]
        
        self.output_dir = os.path.join(self.output_dir, output_dir_name)
        os.makedirs(self.output_dir, exist_ok=True)

        if self.output_type in ["html","both"]:
            shutil.move(output_dir_name + '.report.html', self.output_dir) 
            
        # Copy the JSON report to the output directory if output_type is 'json' or 'both'
        if self.output_type in ["json","both"]:
            shutil.move(output_dir_name + '.report.json', self.output_dir)
            
        # Load the report data from the JSON file and return a LighthouseReport object
        try:
            with open(os.path.join(self.output_dir, output_dir_name + 'report.json'), 'r') as file:
                data = json.load(file)
        except Exception as e:
            log_error(error='[JSON_LOAD_ERROR]', reason=f'{e.__cause__}')
            return None
        return LighthouseReport(file,self.timings)

    def _clean(self):
        pass