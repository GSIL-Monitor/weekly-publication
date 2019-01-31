@echo off

SET heart_pic1=F:\test-weekly\heartbeat\day.json=F:\test-weekly\heartbeat\outfile1.png;
SET heart_pic2=F:\test-weekly\heartbeat\hour.json=F:\test-weekly\heartbeat\outfile2.png;
SET heart_pic3=F:\test-weekly\heartbeat\minute.json=F:\test-weekly\heartbeat\outfile3.png;

SET deduct_pic1=F:\test-weekly\deduct\day.json=F:\test-weekly\deduct\outfile1.png;
SET deduct_pic2=F:\test-weekly\deduct\hour.json=F:\test-weekly\deduct\outfile2.png;
SET deduct_pic3=F:\test-weekly\deduct\minute.json=F:\test-weekly\deduct\outfile3.png;

SET certify_pic1=F:\test-weekly\certify\day.json=F:\test-weekly\certify\outfile1.png;
SET certify_pic2=F:\test-weekly\certify\hour.json=F:\test-weekly\certify\outfile2.png;
SET certify_pic3=F:\test-weekly\certify\minute.json=F:\test-weekly\certify\outfile3.png;

SET initial_pic1=F:\test-weekly\initial\day.json=F:\test-weekly\initial\outfile1.png;
SET initial_pic2=F:\test-weekly\initial\hour.json=F:\test-weekly\initial\outfile2.png;
SET initial_pic3=F:\test-weekly\initial\minute.json=F:\test-weekly\initial\outfile3.png;

SET disconn_pic1=F:\test-weekly\disconn\day.json=F:\test-weekly\disconn\outfile1.png;
SET disconn_pic2=F:\test-weekly\disconn\hour.json=F:\test-weekly\disconn\outfile2.png;
SET disconn_pic3=F:\test-weekly\disconn\minute.json=F:\test-weekly\disconn\outfile3.png;

SET wifi_pic1=F:\test-weekly\wifi\day.json=F:\test-weekly\wifi\outfile1.png;
SET wifi_pic2=F:\test-weekly\wifi\hour.json=F:\test-weekly\wifi\outfile2.png;
SET wifi_pic3=F:\test-weekly\wifi\minute.json=F:\test-weekly\wifi\outfile3.png;

SET cpu_pic=F:\test-weekly\cpu\comparison.json=F:\test-weekly\cpu\comparison.png;
SET mem_pic=F:\test-weekly\mem\comparison.json=F:\test-weekly\mem\comparison.png;
SET clients_pic=F:\test-weekly\clients\comparison.json=F:\test-weekly\clients\comparison.png;

SET order=%cpu_pic%%mem_pic%%clients_pic%%heart_pic1%%heart_pic2%%heart_pic3%%deduct_pic1%%deduct_pic2%%deduct_pic3%%certify_pic1%%certify_pic2%%certify_pic3%%initial_pic1%%initial_pic2%%initial_pic3%%disconn_pic1%%disconn_pic2%%disconn_pic3%%wifi_pic1%%wifi_pic2%%wifi_pic3%

highcharts-export-server -batch "%order%"