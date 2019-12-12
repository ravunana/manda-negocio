import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { AuditoriaVendaDetailComponent } from 'app/entities/auditoria-venda/auditoria-venda-detail.component';
import { AuditoriaVenda } from 'app/shared/model/auditoria-venda.model';

describe('Component Tests', () => {
  describe('AuditoriaVenda Management Detail Component', () => {
    let comp: AuditoriaVendaDetailComponent;
    let fixture: ComponentFixture<AuditoriaVendaDetailComponent>;
    const route = ({ data: of({ auditoriaVenda: new AuditoriaVenda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [AuditoriaVendaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AuditoriaVendaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditoriaVendaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditoriaVenda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
