import logging
import time

logging.basicConfig(filename='app.log', level=logging.INFO)

def log_error(error='FAILED', reason=''):
    logging.error(f'{time.asctime()} [{error}] {reason}')

def log_info(info=''):
    logging.info(f'{time.asctime()} [INFO] {info}')
