import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, Label } from 'ng2-charts';

import { GlucoseService } from '../glucose.service';

@Component({
  selector: 'app-glucose-list',
  templateUrl: './glucose-list.component.html',
  styleUrls: ['./glucose-list.component.scss'],
})
export class GlucoseListComponent implements OnInit {
  formGlucoseEntry: FormGroup;
  chartColumns: String[] = ['Dates', 'mg/dL'];
  myData: any = new Array();

  // Array of different segments in chart
  lineChartData: ChartDataSets[] = [
    { data: [], label: 'Daily Avg Level' }
  ];

  //Labels shown on the x-axis
  lineChartLabels: string[] = [];

  // Define chart options
  lineChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'mg/dL'
        }
      }]
    }     
  };
 

  // Define colors of chart segments
  lineChartColors: Color[] = [

    { // red
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'red',
    }
  ];

  // Set true to show legends
  lineChartLegend = true;

  // Define type of chart
  lineChartType = 'line';

  lineChartPlugins = [];

  constructor(private glucoseService: GlucoseService, public datepipe: DatePipe) {}

  ngOnInit(): void {
    this.formGlucoseEntry = new FormGroup({
      from: new FormControl(null, Validators.required),
      to: new FormControl(null, Validators.required),
    });
  }

  clickGlucoseSubmit() {
    this.glucoseService
      .getAverageGlucoseIntake(this.formGlucoseEntry)
      .subscribe((glucoseData) => {
        this.lineChartLabels = [];
        var start: Date = new Date(this.formGlucoseEntry.get('from').value);
        for (var i: number = 0; i < glucoseData.data.length; i++) {
          if (i != 0) {
            start.setTime(start.getTime() + 1000 * 60 * 60 * 24);
          }
          start = new Date(start);
          let latest_date = this.datepipe.transform(start, 'yyyy-MM-dd');
          this.lineChartLabels.push(latest_date);
        }

        this.lineChartData[0].data = glucoseData.data;
        this.ngOnInit;
      });
  }
}
