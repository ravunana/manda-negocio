import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LocalArmazenamentoDetailComponent } from 'app/entities/local-armazenamento/local-armazenamento-detail.component';
import { LocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

describe('Component Tests', () => {
  describe('LocalArmazenamento Management Detail Component', () => {
    let comp: LocalArmazenamentoDetailComponent;
    let fixture: ComponentFixture<LocalArmazenamentoDetailComponent>;
    const route = ({ data: of({ localArmazenamento: new LocalArmazenamento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LocalArmazenamentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocalArmazenamentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalArmazenamentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localArmazenamento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
