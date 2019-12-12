import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContaDetailComponent } from 'app/entities/conta/conta-detail.component';
import { Conta } from 'app/shared/model/conta.model';

describe('Component Tests', () => {
  describe('Conta Management Detail Component', () => {
    let comp: ContaDetailComponent;
    let fixture: ComponentFixture<ContaDetailComponent>;
    const route = ({ data: of({ conta: new Conta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
