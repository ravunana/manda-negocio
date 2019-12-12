import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { CoordenadaBancariaDetailComponent } from 'app/entities/coordenada-bancaria/coordenada-bancaria-detail.component';
import { CoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

describe('Component Tests', () => {
  describe('CoordenadaBancaria Management Detail Component', () => {
    let comp: CoordenadaBancariaDetailComponent;
    let fixture: ComponentFixture<CoordenadaBancariaDetailComponent>;
    const route = ({ data: of({ coordenadaBancaria: new CoordenadaBancaria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [CoordenadaBancariaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CoordenadaBancariaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoordenadaBancariaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.coordenadaBancaria).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
