import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { LocalizacaoEmpresaDetailComponent } from 'app/entities/localizacao-empresa/localizacao-empresa-detail.component';
import { LocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';

describe('Component Tests', () => {
  describe('LocalizacaoEmpresa Management Detail Component', () => {
    let comp: LocalizacaoEmpresaDetailComponent;
    let fixture: ComponentFixture<LocalizacaoEmpresaDetailComponent>;
    const route = ({ data: of({ localizacaoEmpresa: new LocalizacaoEmpresa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [LocalizacaoEmpresaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocalizacaoEmpresaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocalizacaoEmpresaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.localizacaoEmpresa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
