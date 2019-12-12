import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ClasseContaDetailComponent } from 'app/entities/classe-conta/classe-conta-detail.component';
import { ClasseConta } from 'app/shared/model/classe-conta.model';

describe('Component Tests', () => {
  describe('ClasseConta Management Detail Component', () => {
    let comp: ClasseContaDetailComponent;
    let fixture: ComponentFixture<ClasseContaDetailComponent>;
    const route = ({ data: of({ classeConta: new ClasseConta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ClasseContaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClasseContaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClasseContaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classeConta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
