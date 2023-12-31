# MedicalOrganizations

Веб-приложение "Информационная система медицинских организаций города" (курсовой проект по курсу "Базы Данных")

Технологии: Spring boot, Spring Security, MySQL, React

Задание: 

Каждая больница города состоит из одного или нескольких корпусов, в каждом из которых размещается одно или несколько отделений, специализирующихся на лечении определенной группы болезней; каждое отделение и имеет некоторое количество палат на определенное число коек. Поликлиники могут административно быть прикрепленными к больницам, а могут быть и нет. Как больницы, так и поликлиники обслуживаются врачебным (хирурги, терапевты, невропатологи, окулисты, стоматологи, рентгенологи, гинекологи и пр.) и обслуживающим персоналом (мед. сестры, санитары, уборщицы и пр.). Каждая категория врачебного персонала обладает характеристиками, присущими только специалистам этого профиля и по-разному участвует в связях: хирурги, стоматологи и гинекологи могут проводить операции, они же имеют такие характеристики, как число проведенных операций, число операций с летальным исходом; рентгенологи и стоматологи имеют коэффициент к зарплате за вредные условия труда, у рентгенологов и невропатологов более длительный отпуск. Врачи любого профиля могут иметь степень кандидата или доктора медицинских наук. Степень доктора медицинских наук дает право на присвоение звания профессора, а степень кандидата медицинских наук на присвоение звания доцента. Разрешено совместительство, так что каждый врач может работать либо в больнице, либо в поликлинике, либо и в одной больнице и в одной поликлинике. Врачи со званием доцента или профессора могут консультировать в нескольких больницах или поликлиниках.

Лаборатории, выполняющие те или иные медицинские анализы, могут обслуживать различные больницы и поликлиники, при условии наличия договора на обслуживание с соответствующим лечебным заведением. При этом каждая лаборатория имеет один или несколько профилей: биохимические, физиологические, химические исследования.

Пациенты амбулаторно лечатся в одной из поликлиник, и по направлению из них могут стационарно лечиться либо в больнице, к которой относится поликлиника, либо в любой другой, если специализация больницы, к которой приписана поликлиника не позволяет провести требуемое лечение. Как в больнице, так и в поликлинике ведется персонифицированный учет пациентов, полная история их болезней, все назначения, операции и т.д. В больнице пациент имеет в каждый данный момент одного лечащего врача, в поликлинике - несколько.

Виды запросов в информационной системе:

1. Получить перечень и общее число врачей указанного профиля для конкретного медицинского учреждения, больницы, либо поликлиники, либо всех медицинских учреждений города.
2. Получить перечень и общее число обслуживающего персонала указанной специальности для конкретного медицинского учреждения, больницы, либо поликлиники, либо всех медицинских учреждений города.
3. Получить перечень и общее число врачей указанного профиля, сделавших число операций не менее заданного для конкретного медицинского учреждения, больницы, либо поликлиники, либо всех медицинских учреждений города.
4. Получить перечень и общее число врачей указанного профиля, стаж работы которых не менее заданного для конкретного медицинского учреждения, больницы, либо поликлиники, либо всех медицинских учреждений города.
5. Получить перечень и общее число врачей указанного профиля со степенью кандидата или доктора медицинских наук, со званием доцента или профессора для конкретного медицинского учреждения, либо больницы, либо поликлиники, либо всех медицинских учреждений города.
6. Получить перечень пациентов указанной больницы, отделения, либо конкретной палаты указанного отделения, с указанием даты поступления, состояния, температуры, лечащего врача.
7. Получить перечень пациентов, прошедших стационарное лечение в указанной больнице, либо у конкретного врача за некоторый промежуток времени.
8. Получить перечень пациентов, наблюдающихся в врача указанного профиля в конкретной поликлинике.
9. Получить общее число палат, коек указанной больницы в общем и по каждому отделению, а также число свободных коек по каждому отделению и число полностью свободных палат.
10. Получить общее число кабинетов указанной поликлиники, число посещений каждого кабинета за определенный период.
11. Получить данные о выработке (среднее число принятых пациентов в день) за указанный период для конкретного врача, либо всех врачей поликлиники, либо для всех врачей названного профиля.
12. Получить данные о загрузке (число пациентов, у которых врач в настоящее время является лечащим врачом) для указанного врача, либо всех врачей больницы, либо для всех врачей названного профиля.
13. Получить перечень пациентов, перенесших операции в указанной больнице, либо поликлинике, либо у конкретного врача за некоторый промежуток времени.
14. Получить данные о выработке лаборатории (среднее число проведенных обследований в день) за указанный период для данного медицинского учреждения, либо всех медицинских учреждений города.
