import json
import os
import subprocess
import sys

CURRENT_SCHEMA = os.environ['CANVAS_DATA_SCHEMA_VERSION']
AWS_DATA_TOOLS_JAR_FILE = os.environ['AWS_DATA_TOOLS_JAR']
CANVAS_BINDINGS_JAR_FILE = os.environ['CANVAS_BINDINGS_JAR']
SECURE_PROPERTIES_LOCATION = os.environ['SECURE_PROPERTIES_LOCATION']
RESULT_METADATA = os.environ['CANVAS_DATA_RESULT_FILE']

MAIN_CLASS = 'edu.harvard.canvas_data.aws_data_tools.cli.CanvasDataCli'
CLASSPATH = "{0}:{1}:{2}".format(
        AWS_DATA_TOOLS_JAR_FILE,
        CANVAS_BINDINGS_JAR_FILE,
        SECURE_PROPERTIES_LOCATION
    )

def run_command(args):
    command = ['java', '-Duser.timezone=GMT', '-Xmx2G', '-cp', CLASSPATH, MAIN_CLASS] + args
    print "Running {0}".format(command)
    process = subprocess.Popen(command)
    process.wait()
    print "Return code: {0}".format(process.returncode)
    return process.returncode

def bail(message):
    print message

def download_and_verify():
    status = run_command(['download', RESULT_METADATA])
    if status != 0:
        bail('Failed to download dump')
        return status

    with open(RESULT_METADATA) as result_file:
        download_result = json.load(result_file)

    download_bucket = download_result['AWS_BUCKET']
    download_key = download_result['AWS_KEY']

    status = run_command(['compareschemas', download_bucket, download_key, CURRENT_SCHEMA])
    if status != 0:
        bail('Failed on schema check')
        return status

    status = run_command(['verify', download_bucket, download_key])
    if status != 0:
        bail('Failed to verify dump')
        return status

    return 0

if __name__ == '__main__':
    return_code = download_and_verify()
    sys.exit(return_code)
