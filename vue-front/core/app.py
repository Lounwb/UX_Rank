import shutil
import os
import argparse
from run.runner import LighthouseRunner

def parse_args():
  """Parses command line arguments."""
  parser = argparse.ArgumentParser(
      description='Tool to download dataset images.')
  parser.add_argument('--url', required=True,
                        help='Output path to download images')
  parser.add_argument('--output_dir', required=True,
                        help='Location of dataset.csv')
  args = parser.parse_args()
  return args.url, args.output_dir

def main():
    url, output_dir = parse_args()
    if url.startswith('www'):
       url = 'http://' + url
    u = LighthouseRunner(url, output_dir=output_dir)


if '__main__' == __name__:
    main()