import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { MoradaPessoaDetailComponent } from 'app/entities/morada-pessoa/morada-pessoa-detail.component';
import { MoradaPessoa } from 'app/shared/model/morada-pessoa.model';

describe('Component Tests', () => {
  describe('MoradaPessoa Management Detail Component', () => {
    let comp: MoradaPessoaDetailComponent;
    let fixture: ComponentFixture<MoradaPessoaDetailComponent>;
    const route = ({ data: of({ moradaPessoa: new MoradaPessoa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [MoradaPessoaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MoradaPessoaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MoradaPessoaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.moradaPessoa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
