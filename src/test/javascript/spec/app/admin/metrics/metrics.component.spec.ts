import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RvMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { RvMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
  describe('RvMetricsMonitoringComponent', () => {
    let comp: RvMetricsMonitoringComponent;
    let fixture: ComponentFixture<RvMetricsMonitoringComponent>;
    let service: RvMetricsService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RvMetricsMonitoringComponent]
      })
        .overrideTemplate(RvMetricsMonitoringComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RvMetricsMonitoringComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RvMetricsService);
    });

    describe('refresh', () => {
      it('should call refresh on init', () => {
        // GIVEN
        const response = {
          timers: {
            service: 'test',
            unrelatedKey: 'test'
          },
          gauges: {
            'jcache.statistics': {
              value: 2
            },
            unrelatedKey: 'test'
          }
        };
        spyOn(service, 'getMetrics').and.returnValue(of(response));

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.getMetrics).toHaveBeenCalled();
      });
    });
  });
});
