#！/bin/sh

heart_pic1=/home/domain/weekly-new/file/heartbeat/day.json=/home/domain/weekly-new/file/heartbeat/outfile1.png\;
heart_pic2=/home/domain/weekly-new/file/heartbeat/hour.json=/home/domain/weekly-new/file/heartbeat/outfile2.png\;
heart_pic3=/home/domain/weekly-new/file/heartbeat/minute.json=/home/domain/weekly-new/file/heartbeat/outfile3.png\;

deduct_pic1=/home/domain/weekly-new/file/deduct/day.json=/home/domain/weekly-new/file/deduct/outfile1.png\;
deduct_pic2=/home/domain/weekly-new/file/deduct/hour.json=/home/domain/weekly-new/file/deduct/outfile2.png\;
deduct_pic3=/home/domain/weekly-new/file/deduct/minute.json=/home/domain/weekly-new/file/deduct/outfile3.png\;

certify_pic1=/home/domain/weekly-new/file/certify/day.json=/home/domain/weekly-new/file/certify/outfile1.png\;
certify_pic2=/home/domain/weekly-new/file/certify/hour.json=/home/domain/weekly-new/file/certify/outfile2.png\;
certify_pic3=/home/domain/weekly-new/file/certify/minute.json=/home/domain/weekly-new/file/certify/outfile3.png\;

initial_pic1=/home/domain/weekly-new/file/initial/day.json=/home/domain/weekly-new/file/initial/outfile1.png\;
initial_pic2=/home/domain/weekly-new/file/initial/hour.json=/home/domain/weekly-new/file/initial/outfile2.png\;
initial_pic3=/home/domain/weekly-new/file/initial/minute.json=/home/domain/weekly-new/file/initial/outfile3.png\;

disconn_pic1=/home/domain/weekly-new/file/disconn/day.json=/home/domain/weekly-new/file/disconn/outfile1.png\;
disconn_pic2=/home/domain/weekly-new/file/disconn/hour.json=/home/domain/weekly-new/file/disconn/outfile2.png\;
disconn_pic3=/home/domain/weekly-new/file/disconn/minute.json=/home/domain/weekly-new/file/disconn/outfile3.png\;

wifi_pic1=/home/domain/weekly-new/file/wifi/day.json=/home/domain/weekly-new/file/wifi/outfile1.png\;
wifi_pic2=/home/domain/weekly-new/file/wifi/hour.json=/home/domain/weekly-new/file/wifi/outfile2.png\;
wifi_pic3=/home/domain/weekly-new/file/wifi/minute.json=/home/domain/weekly-new/file/wifi/outfile3.png\;

cpu_pic=/home/domain/weekly-new/file/cpu/comparison.json=/home/domain/weekly-new/file/cpu/comparison.png\;
mem_pic=/home/domain/weekly-new/file/mem/comparison.json=/home/domain/weekly-new/file/mem/comparison.png\;
clients_pic=/home/domain/weekly-new/file/clients/comparison.json=/home/domain/weekly-new/file/clients/comparison.png\;

order=${cpu_pic}${mem_pic}${clients_pic}${heart_pic1}${heart_pic2}${heart_pic3}${deduct_pic1}${deduct_pic2}${deduct_pic3}${certify_pic1}${certify_pic2}${certify_pic3}${initial_pic1}${initial_pic2}${initial_pic3}${disconn_pic1}${disconn_pic2}${disconn_pic3}${wifi_pic1}${wifi_pic2}${wifi_pic3}

echo highcharts-export-server -batch \"${order}\"

/root/.npm-global/bin/highcharts-export-server -batch ${order}

