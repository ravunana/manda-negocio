import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ConversaoUnidadeDetailComponent } from 'app/entities/conversao-unidade/conversao-unidade-detail.component';
import { ConversaoUnidade } from 'app/shared/model/conversao-unidade.model';

describe('Component Tests', () => {
  describe('ConversaoUnidade Management Detail Component', () => {
    let comp: ConversaoUnidadeDetailComponent;
    let fixture: ComponentFixture<ConversaoUnidadeDetailComponent>;
    const route = ({ data: of({ conversaoUnidade: new ConversaoUnidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ConversaoUnidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ConversaoUnidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConversaoUnidadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conversaoUnidade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
